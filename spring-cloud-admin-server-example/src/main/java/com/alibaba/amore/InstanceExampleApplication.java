package com.alibaba.amore;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "de.codecentric.boot.admin.server.web")
public class InstanceExampleApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");
        SpringApplication.run(InstanceExampleApplication.class, args);
    }
}
