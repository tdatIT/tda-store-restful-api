package com.webapp.tdastore.data.payload.response;

import lombok.Data;

@Data
public class WishlistResponse {
    private long wishId;

    private ProductResponse product;
}
