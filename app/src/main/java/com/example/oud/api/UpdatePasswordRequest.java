package com.example.oud.api;

public class UpdatePasswordRequest {
    String currentPassword;
    String password;
    String passwordConfirm;

    public UpdatePasswordRequest(String currentPassword, String password, String passwordConfirm) {
        this.currentPassword = currentPassword;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
