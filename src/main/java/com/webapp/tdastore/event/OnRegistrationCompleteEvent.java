package com.webapp.tdastore.event;

import com.webapp.tdastore.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String applicationContextUrl;


    public OnRegistrationCompleteEvent(User user, String appContextUrl) {
        super(user);
        this.user = user;
        this.applicationContextUrl = appContextUrl;
    }

}
