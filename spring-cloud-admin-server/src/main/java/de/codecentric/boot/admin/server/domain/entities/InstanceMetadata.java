package de.codecentric.boot.admin.server.domain.entities;

import java.util.Map;

public class InstanceMetadata {
    private String serviceName;
    private Map<String, String> metadata;

    // Constructors, getters, and setters
    public InstanceMetadata() {}

    public InstanceMetadata(String serviceName, Map<String, String> metadata) {
        this.serviceName = serviceName;
        this.metadata = metadata;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
