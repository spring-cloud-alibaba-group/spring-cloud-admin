package de.codecentric.boot.admin.server.web;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/search")
public class NacosInstanceController {

    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Autowired
    private Registration registration;

    @GetMapping("/{serviceName}/metadata")
    public List<InstanceMetadata> getInstanceMetadataByServiceName(@PathVariable String serviceName) throws NacosException {
        List<InstanceMetadata> serviceMetadata = new ArrayList<>();
        List<Instance> instances = nacosServiceManager.getNamingService().getAllInstances(serviceName);
        for (Instance instance : instances) {
            InstanceMetadata metadata = new InstanceMetadata(serviceName, instance.getMetadata());
            serviceMetadata.add(metadata);
        }
        return serviceMetadata;
    }

    @GetMapping("/{serviceName}")
    public List<Instance> getInstanceByServiceName(@PathVariable String serviceName) throws NacosException {
        List<InstanceMetadata> serviceMetadata = new ArrayList<>();
        List<Instance> instances = nacosServiceManager.getNamingService().getAllInstances(serviceName);
        return instances;
    }
}