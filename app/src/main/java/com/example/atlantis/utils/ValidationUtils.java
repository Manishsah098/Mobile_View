package com.example.atlantis.utils;

public class ValidationUtils {

    /**
     * Validates phone number.
     * Returns null if valid, or an error message string if invalid.
     */
    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "Please enter your mobile number";
        }

        String cleaned = phoneNumber.replaceAll("[\\s\\-\\(\\)]", "");

        if (!cleaned.matches("\\d+")) {
            return "Phone number must contain only numeric digits";
        }

        if (cleaned.length() < 7 || cleaned.length() > 15) {
            return "Please enter a valid mobile number (7-15 digits)";
        }

        return null;
    }

    /**
     * Validates OTP code.
     * Returns null if valid format, or error string if invalid.
     */
    public static String validateOtp(String otp) {
        if (otp == null || otp.trim().isEmpty()) {
            return "Please enter the 6-digit verification code";
        }

        if (!otp.matches("\\d{6}")) {
            return "Verification code must be exactly 6 digits";
        }

        return null;
    }
}
