package de.codecentric.boot.admin.server.web;

import de.codecentric.boot.admin.server.domain.entities.InstanceMetadata;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AdminController
@RestController
@RequestMapping("/admin")
public class InstanceController {

    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Autowired
    private Registration registration;

    @GetMapping("/instance/all-instances")
    public List<Instance> getAllInstances() throws NacosException {
        List<String> services = nacosServiceManager.getNamingService().getServicesOfServer(1, 100).getData();
        List<Instance> allInstances = new ArrayList<>();
        for (String service : services) {
            allInstances.addAll(nacosServiceManager.getNamingService().getAllInstances(service));
        }
        return allInstances;
    }

    @GetMapping("/instance/all-healthy-instances")
    public List<Instance> getAllHealthyInstances() throws NacosException {
        List<String> services = nacosServiceManager.getNamingService().getServicesOfServer(1, 100).getData();
        List<Instance> allHealthyInstances = new ArrayList<>();
        for (String service : services) {
            allHealthyInstances.addAll(nacosServiceManager.getNamingService().selectInstances(service, true));
        }
        return allHealthyInstances;
    }

    @GetMapping("/instance/all-metadata")
    public List<InstanceMetadata> getAllInstanceMetadata() throws NacosException {
        List<String> services = nacosServiceManager.getNamingService().getServicesOfServer(1, 100).getData();
        List<InstanceMetadata> allMetadata = new ArrayList<>();
        for (String service : services) {
            List<Instance> instances = nacosServiceManager.getNamingService().getAllInstances(service);
            for (Instance instance : instances) {
                InstanceMetadata metadata = new InstanceMetadata(service, instance.getMetadata());
                allMetadata.add(metadata);
            }
        }
        return allMetadata;
    }

    @GetMapping("/instance/all-healthy-metadata")
    public List<InstanceMetadata> getAllHealthyInstanceMetadata() throws NacosException {
        List<String> services = nacosServiceManager.getNamingService().getServicesOfServer(1, 100).getData();
        List<InstanceMetadata> allHealthyMetadata = new ArrayList<>();
        for (String service : services) {
            List<Instance> instances = nacosServiceManager.getNamingService().getAllInstances(service);
            for (Instance instance : instances) {
                if (instance.isHealthy()) {
                    InstanceMetadata metadata = new InstanceMetadata(service, instance.getMetadata());
                    allHealthyMetadata.add(metadata);
                }
            }
        }
        return allHealthyMetadata;
    }

    @GetMapping("/instance/{serviceName}/metadata")
    public List<InstanceMetadata> getInstanceMetadataByServiceName(@PathVariable String serviceName) throws NacosException {
        List<InstanceMetadata> serviceMetadata = new ArrayList<>();
        List<Instance> instances = nacosServiceManager.getNamingService().getAllInstances(serviceName);
        for (Instance instance : instances) {
            InstanceMetadata metadata = new InstanceMetadata(serviceName, instance.getMetadata());
            serviceMetadata.add(metadata);
        }
        return serviceMetadata;
    }
}
