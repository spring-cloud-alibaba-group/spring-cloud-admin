package de.codecentric.boot.admin.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceStatu {
    private static final Logger logger = LoggerFactory.getLogger(ServiceStatu.class);

    private String instanceStatus;

    public String getInstanceStatus() {
        return instanceStatus;
    }

    public void setInstanceStatus(String instanceStatus) {
        this.instanceStatus = instanceStatus;
        logger.info("Instance status updated to: {}", instanceStatus);
    }
}
