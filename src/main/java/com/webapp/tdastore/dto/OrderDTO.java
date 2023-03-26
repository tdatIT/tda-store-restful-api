package com.webapp.tdastore.dto;

import com.webapp.tdastore.config.ValidatorUtils;
import com.webapp.tdastore.entities.UserAddress;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class OrderDTO {
    @NotBlank
    @Pattern(regexp = ValidatorUtils.VIETNAMESE_REGEX)
    private String firstname;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.VIETNAMESE_REGEX)
    private String lastname;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.EMAIL_REGEX)
    private String email;

    @Pattern(regexp = ValidatorUtils.PHONE_REGEX)
    private String phone;

    @NotNull
    private UserAddress defaultAddress;
}
