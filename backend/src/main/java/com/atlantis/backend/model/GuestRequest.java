package com.atlantis.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "guest_requests")
public class GuestRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String serviceName; // e.g. "Housekeeping", "Spa & Wellness", "Transport"
    private String details;
    private String status;      // e.g. "Pending", "In Progress", "Completed"
    private LocalDateTime createdAt = LocalDateTime.now();

    public GuestRequest() {}

    public GuestRequest(Long userId, String serviceName, String details, String status) {
        this.userId = userId;
        this.serviceName = serviceName;
        this.details = details;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
