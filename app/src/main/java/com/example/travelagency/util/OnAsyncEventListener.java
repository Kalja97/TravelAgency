package com.example.travelagency.util;

public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}