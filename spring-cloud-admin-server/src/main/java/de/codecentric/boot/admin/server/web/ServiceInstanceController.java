package de.codecentric.boot.admin.server.web;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/service")
public class ServiceInstanceController {

    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Autowired
    private Registration registration;

    @GetMapping("/instances")
    public List<Instance> getAll(@RequestParam String serviceName) throws NacosException {
        return nacosServiceManager.getNamingService().getAllInstances(serviceName); // test->serviceName=configServer
    }

    @GetMapping("/services")
    public List<String> getServices() throws NacosException {
        return nacosServiceManager.getNamingService().getServicesOfServer(1, 100).getData();
    }

    @GetMapping("/healthy")
    public Instance getHealthyInstance(@RequestParam String serviceName) throws NacosException {
        return nacosServiceManager.getNamingService().selectOneHealthyInstance(serviceName);
    }

}
