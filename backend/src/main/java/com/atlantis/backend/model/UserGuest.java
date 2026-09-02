package com.atlantis.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "guest_users")
public class UserGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;          // e.g. "Mr.", "Ms.", "Dr."
    private String fullName;       // e.g. "Aman Singh"
    private String phoneNumber;    // e.g. "501234567"
    private String countryCode;    // e.g. "+971"
    private String roomNumber;     // e.g. "305"
    private String roomType;       // e.g. "Deluxe Ocean View"
    private String checkInDate;    // e.g. "Jul 26, 2026"
    private String checkOutDate;   // e.g. "Jul 30, 2026"
    private String avatarUrl;      // e.g. "app/src/main/res/drawable/guest_avatar.jpg"
    private Integer notificationCount = 3;

    public UserGuest() {}

    public UserGuest(String title, String fullName, String phoneNumber, String countryCode, String roomNumber, String roomType, String checkInDate, String checkOutDate, String avatarUrl) {
        this.title = title;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.avatarUrl = avatarUrl;
        this.notificationCount = 3;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public Integer getNotificationCount() { return notificationCount; }
    public void setNotificationCount(Integer notificationCount) { this.notificationCount = notificationCount; }
}
