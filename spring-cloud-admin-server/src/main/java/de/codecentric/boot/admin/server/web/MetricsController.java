package de.codecentric.boot.admin.server.web;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    /**
     * Autowired PrometheusMeterRegistry used for scraping and exposing metrics.
     */
    @Autowired
    private PrometheusMeterRegistry prometheusMeterRegistry;

    /**
     * Exposes the /metrics endpoint which Prometheus will scrape to gather metrics data.
     *
     * @return String - All collected Prometheus metrics data.
     */
    @GetMapping("/metrics")
    @ResponseBody
    public String scrapeMetrics() {
        // Return all collected Prometheus metrics data
        String metricsData = prometheusMeterRegistry.scrape();
        logMetricsData(metricsData);
        return metricsData;
    }

    /**
     * Logs the scraped metrics data for debugging purposes.
     *
     * @param metricsData - The collected Prometheus metrics data.
     */
    private void logMetricsData(String metricsData) {
        // Log metrics data for further debugging
        System.out.println("Prometheus Metrics Data:");
        System.out.println(metricsData);
    }

    /**
     * Example: Retrieves the value of a specific metric by name.
     *
     * @param metricName - The name of the metric.
     * @return String - The value of the specified metric.
     */
    @GetMapping("/metric-value")
    @ResponseBody
    public String getMetricValue(String metricName) {
        // Find and return the value of the specified metric
        double metricValue = getMetricValueFromRegistry(metricName);
        return String.format("Metric Value for %s: %f", metricName, metricValue);
    }

    /**
     * Retrieves the value of a specified metric from the PrometheusMeterRegistry.
     *
     * @param metricName - The name of the metric to retrieve.
     * @return double - The value of the specified metric. Returns 0.0 if the metric is not found.
     */
    private double getMetricValueFromRegistry(String metricName) {
        // Retrieve the gauge for the specified metric name
        Gauge gauge = prometheusMeterRegistry.find(metricName).gauge();

        // Check if the gauge exists and return its value
        if (gauge != null) {
            return gauge.value();
        } else {
            // If the gauge does not exist, return 0 as a default value
            System.out.println("Metric not found: " + metricName);
            return 0.0;
        }
    }


    /**
     * Health check endpoint to verify the metrics service is running.
     *
     * @return String - Health status message.
     */
    @GetMapping("/health")
    @ResponseBody
    public String healthCheck() {
        return "Metrics service is up and running!";
    }

    /**
     * Retrieves the total number of collected metrics in the system.
     *
     * @return String - The total count of metrics.
     */
    @GetMapping("/metric-count")
    @ResponseBody
    public String getMetricCount() {
        long count = prometheusMeterRegistry.getMeters().size();
        return String.format("Total Metrics Count: %d", count);
    }
}
