package com.webapp.tdastore.dto;

import com.webapp.tdastore.entities.UserAddress;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderUserDTO {
    @NotNull
    private UserAddress address;
    private boolean paypal;
}
