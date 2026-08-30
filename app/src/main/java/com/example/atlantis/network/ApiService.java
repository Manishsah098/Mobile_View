package com.example.atlantis.network;

public interface ApiService {

    void sendOtp(
        String phoneNumber,
        ApiCallback callback
    );

    void verifyOtp(
        String phoneNumber,
        String otp,
        ApiCallback callback
    );

    void getGuestProfile(
        String token,
        GuestCallback callback
    );
}
