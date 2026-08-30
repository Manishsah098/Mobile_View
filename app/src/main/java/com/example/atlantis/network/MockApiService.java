package com.example.atlantis.network;

import android.os.Handler;
import android.os.Looper;
import com.example.atlantis.model.Guest;

public class MockApiService implements ApiService {

    private static final String VALID_OTP = "123456";
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static MockApiService instance;

    public static synchronized MockApiService getInstance() {
        if (instance == null) {
            instance = new MockApiService();
        }
        return instance;
    }

    @Override
    public void sendOtp(final String phoneNumber, final ApiCallback callback) {
        // Simulate network latency of 500ms
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                    callback.onError("Please enter a valid mobile number.");
                } else {
                    callback.onSuccess("Verification code sent on WhatsApp to " + phoneNumber);
                }
            }
        }, 500);
    }

    @Override
    public void verifyOtp(final String phoneNumber, final String otp, final ApiCallback callback) {
        // Simulate network latency of 600ms
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (otp != null && otp.trim().equals(VALID_OTP)) {
                    callback.onSuccess("Verification successful");
                } else {
                    callback.onError("Invalid verification code. Please try again.");
                }
            }
        }, 600);
    }

    @Override
    public void getGuestProfile(final String token, final GuestCallback callback) {
        // Simulate network latency of 500ms
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token == null || token.trim().isEmpty()) {
                    callback.onError("Session expired. Please log in again.");
                    return;
                }
                
                // Construct luxury guest profile
                Guest guest = new Guest(
                    "GST-98421",
                    "Mr. Aman Singh",
                    "+971 50 123 4567",
                    "305",
                    "Deluxe Ocean View",
                    "Jul 26, 2026",
                    "Jul 31, 2026",
                    "guest_avatar",
                    "ATR-305-2026"
                );
                callback.onSuccess(guest);
            }
        }, 500);
    }
}
