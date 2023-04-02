package com.webapp.tdastore.data.payload.response;

import com.webapp.tdastore.config.ValidatorUtils;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ChangePassword {
    @Pattern(regexp = ValidatorUtils.PASSWORD_REGEX)
    private String new_pass, old_pass;
}
