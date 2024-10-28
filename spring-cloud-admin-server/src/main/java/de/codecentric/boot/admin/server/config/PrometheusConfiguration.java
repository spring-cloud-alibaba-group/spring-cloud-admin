package de.codecentric.boot.admin.server.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfiguration {

    /**
     * Creates and configures a PrometheusMeterRegistry.
     *
     * @return PrometheusMeterRegistry - an instance of the Prometheus meter registry
     * using the default Prometheus configuration.
     */
    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        // PrometheusConfig.DEFAULT is the default configuration
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

    /**
     * Registers the MeterRegistry into the application's context and applies common
     * tags (e.g., service name) to all metrics.
     *
     * @return MeterRegistryCustomizer<MeterRegistry> - a customizer for the MeterRegistry
     * that adds common tags to all metrics.
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        // Add application tags (e.g., service name) to all metrics
        return registry -> registry.config().commonTags("application", "nacos-service");
    }
}
