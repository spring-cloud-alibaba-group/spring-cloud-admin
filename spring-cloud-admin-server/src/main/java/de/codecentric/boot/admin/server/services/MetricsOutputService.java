package de.codecentric.boot.admin.server.services;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricsOutputService {

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
     * Initializes Nacos service metrics by registering custom gauges for instance count
     * and instance weight for a given service.
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

    /**
     * Retrieves all registered metrics from the MeterRegistry and returns them as a list of strings.
     *
     * @return List<String> - A list of metrics in string format, including the metric name, tags, and measurements.
     */
    public List<String> getAllMetrics() {
        List<String> metricsList = new ArrayList<>();

        // Iterate over all registered meters in the registry
        for (Meter meter : meterRegistry.getMeters()) {
            StringBuilder metricInfo = new StringBuilder("Metric: " + meter.getId().getName() + "\n");

            // Retrieve and format the tags
            String tags = meter.getId().getTags().stream()
                    .map(tag -> tag.getKey() + "=" + tag.getValue())
                    .collect(Collectors.joining(", "));
            metricInfo.append("Tags: ").append(tags).append("\n");

            // Retrieve and format the measurements
            meter.measure().forEach(measurement -> {
                metricInfo.append("Measurement: ")
                        .append(measurement.getStatistic().name())
                        .append(" = ").append(measurement.getValue()).append("\n");
            });

            metricsList.add(metricInfo.toString());
        }

        return metricsList;
    }
}
