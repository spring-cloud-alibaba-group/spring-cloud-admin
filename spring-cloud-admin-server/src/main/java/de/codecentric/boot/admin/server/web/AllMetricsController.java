package de.codecentric.boot.admin.server.web;

import de.codecentric.boot.admin.server.services.MetricsOutputService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AllMetricsController {

    /**
     * Injecting the custom MetricsOutputService to retrieve metrics data.
     */
    private MetricsOutputService metricsOutputService = new MetricsOutputService();

    /**
     * Exposes the /all-metrics endpoint to display all registered metrics.
     *
     * @return List<String> - A list of strings representing all registered metrics.
     */
    @GetMapping("/all-metrics")
    public List<String> getAllMetrics() {
        // Call the service to retrieve all metrics
        return metricsOutputService.getAllMetrics();
    }
}
