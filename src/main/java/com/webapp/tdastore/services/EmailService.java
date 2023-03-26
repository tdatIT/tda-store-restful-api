package com.webapp.tdastore.services;

public interface EmailService {
    void sendVerifyEmail(String email, String token);

    void sendResetPassword(String email, String code);
}
