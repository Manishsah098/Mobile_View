package com.atlantis.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtlantisBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtlantisBackendApplication.class, args);
        System.out.println("=================================================================");
        System.out.println("🚀 Atlantis The Royal - Spring Boot Backend Server Started!");
        System.out.println("📍 REST API Base URL: http://localhost:8080/api/dashboard/data");
        System.out.println("📍 Registration API:  http://localhost:8080/api/auth/register");
        System.out.println("=================================================================");
    }
}
