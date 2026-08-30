package com.example.atlantis.network;

import com.example.atlantis.model.Guest;

public interface GuestCallback {
    void onSuccess(Guest guest);
    void onError(String error);
}
