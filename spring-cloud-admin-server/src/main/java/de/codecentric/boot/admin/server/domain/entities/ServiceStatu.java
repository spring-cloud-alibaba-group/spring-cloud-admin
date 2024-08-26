package de.codecentric.boot.admin.server.domain.entities;

import org.springframework.stereotype.Service;

@Service
public class ServiceStatu {
    private String instanceStatus = "UNKNOWN";

    public String getInstanceStatus() {
        return instanceStatus;
    }

    public void setInstanceStatus(String instanceStatus) {
        this.instanceStatus = instanceStatus;
    }
}
