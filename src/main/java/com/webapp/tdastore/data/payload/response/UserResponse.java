package com.webapp.tdastore.data.payload.response;

import com.webapp.tdastore.data.entities.Role;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResponse {

    private long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String avatar;
    private boolean status;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Role role;
}
