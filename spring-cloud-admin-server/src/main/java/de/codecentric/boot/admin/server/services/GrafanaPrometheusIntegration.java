package de.codecentric.boot.admin.server.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * The GrafanaPrometheusIntegration class demonstrates how to integrate Grafana with Prometheus using Java.
 * This represents a higher level of abstraction since Grafana data sources are usually set up via its UI.
 */
public class GrafanaPrometheusIntegration {

    // URL to the Prometheus server that Grafana will use as a data source.
    private static final String PROMETHEUS_BASE_URL = "http://localhost:9090";

    // Example expression query to fetch metrics data from Prometheus.
    private static final String QUERY = "/api/v1/query?query=up";

    /**
     * Main method to simulate data fetching from Prometheus which Grafana uses to visualize.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        try {
            // Fetch metrics from Prometheus using the Prometheus HTTP API.
            String jsonResponse = fetchPrometheusMetrics(PROMETHEUS_BASE_URL + QUERY);

            // Process the JSON response and visualize metrics (hypothetical visualization).
            visualizeMetrics(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simulates an HTTP request to Prometheus to retrieve metrics data as a JSON string.
     *
     * @param urlString The complete URL with the Prometheus API query.
     * @return A JSON string representing the metrics data.
     * @throws IOException If there is an issue with the HTTP connection or data retrieval.
     */
    public static String fetchPrometheusMetrics(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Check if the connection is made successfully
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }

        // Use a Scanner to read the incoming data stream from Prometheus
        Scanner sc = new Scanner(url.openStream());
        StringBuilder jsonResponse = new StringBuilder();
        while (sc.hasNext()) {
            jsonResponse.append(sc.nextLine());
        }
        sc.close();

        return jsonResponse.toString();
    }

    /**
     * Processes and visualizes the received metrics data.
     * In a real-world scenario, data will be processed to plug into Grafana's dashboards.
     *
     * @param jsonData The data fetched from Prometheus as a JSON string.
     */
    public static void visualizeMetrics(String jsonData) {
        // Print the JSON response for demonstration purposes
        System.out.println("Metrics data received from Prometheus:");
        System.out.println(jsonData);

        // Additional processing would be done here to transform JSON data
        // into a format suitable for graphical or tabular display via Grafana.
        // This could involve parsing JSON, extracting specific metrics,
        // and potentially storing the results in a database.
    }
}
