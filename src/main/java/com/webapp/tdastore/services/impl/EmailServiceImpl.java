package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.config.AppVariable;
import com.webapp.tdastore.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerifyEmail(String email, String token) {
        String recipientAddress = email;
        String subject = "Xác thực tài khoản";
        String confirmationUrl
                = AppVariable.APP_CONTEXT_URL + "/verify-account?token=" + token;
        String message = "Xác thực tài khoản";

        SimpleMailMessage mail_data = new SimpleMailMessage();
        mail_data.setTo(recipientAddress);
        mail_data.setSubject(subject);
        mail_data.setText(message + "\r\n" + confirmationUrl);
        mailSender.send(mail_data);
        logger.info("Send email verify account [" + email + "]");
    }

    @Override
    public void sendResetPassword(String email, String code) {
        String recipientAddress = email;
        String subject = "Đặt lại mật khẩu";
        String message = "Mã đặt lại mật khẩu :" + code;

        SimpleMailMessage mail_data = new SimpleMailMessage();
        mail_data.setTo(recipientAddress);
        mail_data.setSubject(subject);
        mail_data.setText(message);
        mailSender.send(mail_data);
        logger.info("Send email verify account [" + email + "]");
    }
}
