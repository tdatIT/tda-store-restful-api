package com.webapp.tdastore.dto;

import com.webapp.tdastore.config.ValidatorUtils;
import com.webapp.tdastore.entities.Order;
import com.webapp.tdastore.entities.Role;
import com.webapp.tdastore.entities.UserAddress;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@Data
public class UserDTO {

    private long userId;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.VIETNAMESE_REGEX)
    private String firstname;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.VIETNAMESE_REGEX)
    private String lastname;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.EMAIL_REGEX)
    private String email;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.PASSWORD_REGEX)
    private String hashPassword;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.PASSWORD_REGEX)
    private String confirmPassword;

    @Pattern(regexp = ValidatorUtils.PHONE_REGEX)
    private String phone;

    @NotNull
    private UserAddress defaultAddress;

    private String avatar;

    private boolean status;

    private List<UserAddress> address;

    private List<Order> orders;

    private Role role;
    private Timestamp createDate;
    @NotBlank
    @Pattern(regexp = ValidatorUtils.VIETNAMESE_REGEX)
    private String address_detail;

}
