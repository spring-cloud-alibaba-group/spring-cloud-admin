package de.codecentric.boot.admin.server.services;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MyCustomMetrics {

    private final MeterRegistry meterRegistry;

    public MyCustomMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        meterRegistry.counter("custom_metric_counter", "type", "custom");
    }

    public void recordCustomMetric() {
        meterRegistry.counter("custom_metric_counter", "type", "custom").increment();
    }
}

