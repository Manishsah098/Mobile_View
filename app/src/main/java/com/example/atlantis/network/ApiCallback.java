package com.example.atlantis.network;

public interface ApiCallback {
    void onSuccess(String message);
    void onError(String error);
}
