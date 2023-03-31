package com.webapp.tdastore.data.payload;

import com.webapp.tdastore.config.ValidatorUtils;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ResetPass {
    @NotEmpty
    @Pattern(regexp = ValidatorUtils.EMAIL_REGEX)
    private String email;

    private String token;
    @Pattern(regexp = ValidatorUtils.PASSWORD_LOGIN_REGEX)
    private String old_pass;
    @Pattern(regexp = ValidatorUtils.PASSWORD_REGEX)
    private String new_pass;
}
