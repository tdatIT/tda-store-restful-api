package com.webapp.tdastore.data.payload;

import com.webapp.tdastore.config.ValidatorUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    @Pattern(regexp = ValidatorUtils.EMAIL_REGEX)
    private String email;

    @NotBlank
    @Pattern(regexp = ValidatorUtils.PASSWORD_LOGIN_REGEX)
    private String password;
}
