package com.webapp.tdastore.data.dto;

import com.webapp.tdastore.data.entities.UserAddress;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderUserDTO {
    @NotNull
    private UserAddress address;
    private boolean paypal;
}
