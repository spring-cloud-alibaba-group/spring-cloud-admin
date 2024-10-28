package de.codecentric.boot.admin.server.domain.entities;

import java.util.Map;

public class InstanceMetadata {
    private String serviceName; // The name of the service
    private Map<String, String> metadata; // Metadata associated with the service instance

    // Default constructor
    public InstanceMetadata() {}

    /**
     * Constructor to initialize InstanceMetadata with service name and metadata.
     *
     * @param serviceName - The name of the service.
     * @param metadata    - A map containing metadata for the service instance.
     */
    public InstanceMetadata(String serviceName, Map<String, String> metadata) {
        this.serviceName = serviceName;
        this.metadata = metadata;
    }

    /**
     * Retrieves the name of the service.
     *
     * @return String - The name of the service.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the name of the service.
     *
     * @param serviceName - The name of the service to set.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Retrieves the metadata associated with the service instance.
     *
     * @return Map<String, String> - A map of metadata for the service instance.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata for the service instance.
     *
     * @param metadata - A map of metadata to set for the service instance.
     */
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
