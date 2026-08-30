package com.example.atlantis.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.atlantis.model.Guest;

public class SessionManager {

    private static final String PREF_NAME = "AtlantisGuestSession";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_GUEST_ID = "guest_id";
    private static final String KEY_GUEST_NAME = "guest_name";
    private static final String KEY_PHONE = "guest_phone";
    private static final String KEY_ROOM_NUMBER = "room_number";
    private static final String KEY_ROOM_TYPE = "room_type";
    private static final String KEY_CHECK_IN_DATE = "check_in_date";
    private static final String KEY_CHECK_OUT_DATE = "check_out_date";
    private static final String KEY_BOOKING_ID = "booking_id";
    private static final String KEY_PROFILE_IMAGE = "profile_image";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private static SessionManager instance;

    public SessionManager(Context context) {
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveGuestSession(String token, Guest guest) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_AUTH_TOKEN, token);
        if (guest != null) {
            editor.putString(KEY_GUEST_ID, guest.getId());
            editor.putString(KEY_GUEST_NAME, guest.getName());
            editor.putString(KEY_PHONE, guest.getPhone());
            editor.putString(KEY_ROOM_NUMBER, guest.getRoomNumber());
            editor.putString(KEY_ROOM_TYPE, guest.getRoomType());
            editor.putString(KEY_CHECK_IN_DATE, guest.getCheckInDate());
            editor.putString(KEY_CHECK_OUT_DATE, guest.getCheckOutDate());
            editor.putString(KEY_BOOKING_ID, guest.getBookingId());
            editor.putString(KEY_PROFILE_IMAGE, guest.getProfileImage());
        }
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN, "ATR-TOKEN-MOCK-98421");
    }

    public String getGuestName() {
        return pref.getString(KEY_GUEST_NAME, "Mr. Aman Singh");
    }

    public String getRoomNumber() {
        return pref.getString(KEY_ROOM_NUMBER, "305");
    }

    public String getRoomType() {
        return pref.getString(KEY_ROOM_TYPE, "Deluxe Ocean View");
    }

    public String getCheckInDate() {
        return pref.getString(KEY_CHECK_IN_DATE, "Jul 26, 2026");
    }

    public String getCheckOutDate() {
        return pref.getString(KEY_CHECK_OUT_DATE, "Jul 31, 2026");
    }

    public String getBookingId() {
        return pref.getString(KEY_BOOKING_ID, "ATR-305-2026");
    }

    public Guest getGuest() {
        if (!isLoggedIn()) {
            return null;
        }
        return new Guest(
            pref.getString(KEY_GUEST_ID, "GST-98421"),
            pref.getString(KEY_GUEST_NAME, "Mr. Aman Singh"),
            pref.getString(KEY_PHONE, "+971 50 123 4567"),
            pref.getString(KEY_ROOM_NUMBER, "305"),
            pref.getString(KEY_ROOM_TYPE, "Deluxe Ocean View"),
            pref.getString(KEY_CHECK_IN_DATE, "Jul 26, 2026"),
            pref.getString(KEY_CHECK_OUT_DATE, "Jul 31, 2026"),
            pref.getString(KEY_PROFILE_IMAGE, "guest_avatar"),
            pref.getString(KEY_BOOKING_ID, "ATR-305-2026")
        );
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
