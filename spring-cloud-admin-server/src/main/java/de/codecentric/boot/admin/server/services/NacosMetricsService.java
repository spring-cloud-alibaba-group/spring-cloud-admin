package de.codecentric.boot.admin.server.services;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NacosMetricsService {

    /**
     * Autowired Micrometer's MeterRegistry to record Prometheus metrics.
     */
    @Autowired
    private MeterRegistry meterRegistry;

    /**
     * Autowired Nacos NamingService to interact with Nacos.
     */
    @Autowired
    private NamingService namingService;

    /**
     * Constructor for NacosMetricsService.
     *
     * @param meterRegistry - MeterRegistry used for recording metrics.
     * @param namingService - NamingService used to interact with Nacos for service discovery.
     */
    public NacosMetricsService(MeterRegistry meterRegistry, NamingService namingService) {
        this.meterRegistry = meterRegistry;
        this.namingService = namingService;
    }

    /**
     * Initializes Nacos service metrics by registering custom gauges for the number of instances and instance weight.
     *
     * @param serviceName - The name of the Nacos service.
     * @param groupName   - The group name of the Nacos service.
     * @throws NacosException - If there is an error retrieving instances from Nacos.
     */
    public void initNacosMetrics(String serviceName, String groupName) throws NacosException {
        // Retrieve the list of instances for the specified service from Nacos
        List<Instance> instances = namingService.getAllInstances(serviceName, groupName);

        // Register a gauge to record the number of instances for the specified Nacos service
        Gauge.builder("nacos.service.instances.count", instances, List::size)
                .description("Number of instances for Nacos service: " + serviceName)
                .tags("serviceName", serviceName, "groupName", groupName)
                .register(meterRegistry);

        // Register custom gauges for other relevant metrics (e.g., CPU usage, memory)
        for (Instance instance : instances) {
            Gauge.builder("nacos.service.instance.weight", instance, Instance::getWeight)
                    .description("Instance weight for service: " + serviceName)
                    .tags("instanceId", instance.getInstanceId(), "ip", instance.getIp())
                    .register(meterRegistry);
        }
    }
}
