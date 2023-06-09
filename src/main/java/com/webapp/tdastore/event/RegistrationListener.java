package com.webapp.tdastore.event;

import com.webapp.tdastore.data.entities.User;
import com.webapp.tdastore.services.EmailService;
import com.webapp.tdastore.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Log4j2
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    final Logger logger = LoggerFactory.getLogger(RegistrationListener.class);
    @Autowired
    private UserService service;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = String.valueOf(new Random().nextInt(999999) + 100000);
        service.createVerificationToken(user, token);
        emailService.sendVerifyEmail(user.getEmail(), token);
    }
}
