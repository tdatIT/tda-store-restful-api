package com.webapp.tdastore.data.payload.response;

import lombok.Data;

@Data
public class AddressResponse {
    private long addressId;
    private String phone, addressDetail;

}
