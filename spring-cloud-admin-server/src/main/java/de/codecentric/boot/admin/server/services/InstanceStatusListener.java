package de.codecentric.boot.admin.server.services;

import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.pojo.Instance;
import de.codecentric.boot.admin.server.domain.entities.ServiceStatu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InstanceStatusListener {

    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private ServiceStatu serviceStatu;

    @PostConstruct
    public void init() {
        try {
            String serviceName = "configServer";
            nacosServiceManager.getNamingService().subscribe(serviceName, new EventListener() {
                @Override
                public void onEvent(Event event) {
                    if (event instanceof Instance instance) {
                        if (instance.getInstanceHeartBeatInterval() > 0) {
                            serviceStatu.setInstanceStatus("UP");
                        } else {
                            serviceStatu.setInstanceStatus("DOWN");
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

