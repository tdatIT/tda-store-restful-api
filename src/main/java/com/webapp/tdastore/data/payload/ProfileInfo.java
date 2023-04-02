package com.webapp.tdastore.data.payload;

import com.webapp.tdastore.config.ValidatorUtils;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ProfileInfo {
    @Pattern(regexp = ValidatorUtils.VIETNAMESE_REGEX)
    private String firstName, lastName;
    @Pattern(regexp = ValidatorUtils.PHONE_REGEX)
    private String phone;

}
