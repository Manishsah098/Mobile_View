package com.atlantis.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_orders")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String itemName;
    private Double price;
    private String discountApplied;
    private String roomNumber;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();

    public FoodOrder() {}

    public FoodOrder(Long userId, String itemName, Double price, String discountApplied, String roomNumber, String status) {
        this.userId = userId;
        this.itemName = itemName;
        this.price = price;
        this.discountApplied = discountApplied;
        this.roomNumber = roomNumber;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDiscountApplied() { return discountApplied; }
    public void setDiscountApplied(String discountApplied) { this.discountApplied = discountApplied; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
