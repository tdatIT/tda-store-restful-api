package com.webapp.tdastore.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LoginResponse {
    public int code;
    public String message;
    public String jwt;
    public Date expiryDate;

    public UserResponse user;

}
