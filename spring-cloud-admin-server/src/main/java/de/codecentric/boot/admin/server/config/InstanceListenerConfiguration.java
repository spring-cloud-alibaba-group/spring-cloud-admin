package de.codecentric.boot.admin.server.config;

import com.alibaba.cloud.nacos.NacosServiceManager;
import de.codecentric.boot.admin.server.domain.entities.ServiceStatu;
import de.codecentric.boot.admin.server.services.InstanceStatusListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient  // Enable Spring Cloud service discovery
@Slf4j
public class InstanceListenerConfiguration {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServerAddress;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${nacos.service.config.watch-delay:3000}")
    private Long watchDelay;

    /**
     * Configure the NacosServiceManager bean to interact with the Nacos registry.
     */
    @Bean
    public NacosServiceManager nacosServiceManager() {
        log.info("Initializing Nacos Service Manager with server address: {}", nacosServerAddress);
        NacosServiceManager nacosServiceManager = new NacosServiceManager();
        // Additional Nacos service-related configurations can be added here
        return nacosServiceManager;
    }

    /**
     * Bean to store service status.
     */
    @Bean
    public ServiceStatu serviceStatu() {
        log.info("Initializing ServiceStatu bean for application: {}", applicationName);
        return new ServiceStatu();
    }

    /**
     * Configure the listener to track Nacos instance status changes.
     * @param nacosServiceManager Nacos service manager
     * @param serviceStatu Service status object
     * @return InstanceStatusListener instance
     */
    @Bean
    public InstanceStatusListener instanceStatusListener(NacosServiceManager nacosServiceManager, ServiceStatu serviceStatu) {
        log.info("Initializing InstanceStatusListener for application: {}", applicationName);
        return new InstanceStatusListener();
    }

    /**
     * Configure Nacos auto-service registration to register this service into Nacos.
     */
    @Bean
    @Autowired
    public NacosServiceManager nacosAutoServiceRegistration(NacosServiceManager nacosServiceManager) {
        log.info("Configuring Nacos auto service registration for application: {}", applicationName);
        return nacosServiceManager;
    }

    /**
     * Monitor Nacos configuration changes, such as service heartbeat or instance status updates.
     */
    @Bean
    public void monitorNacosConfigChanges() {
        log.info("Monitoring Nacos config changes with delay: {} ms", watchDelay);
        // Custom logic to monitor Nacos configuration changes can be added here
    }

}

