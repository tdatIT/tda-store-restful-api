package com.webapp.tdastore.data.payload;

import com.webapp.tdastore.config.ValidatorUtils;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AddressRequest {
    private int province_code, district_code, ward_code;
    @Pattern(regexp = ValidatorUtils.VIETNAMESE_REGEX)
    private String detail;
    @Pattern(regexp = ValidatorUtils.PHONE_REGEX)
    private String phone;
}
