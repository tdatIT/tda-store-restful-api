package com.webapp.tdastore.services.impl;

import com.webapp.tdastore.services.ShippingService;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {
    final int P_LOCATION_CODE = 79;
    final int D_LOCATION_CODE = 769;
    final int DEFAULT_COST = 18000;
    final float OUTSKIRTS = 2;
    final float URBAN = 1;

    @Override
    public int getPriceFromUserAddress(int province_code, int district) {
        int final_cost = 0;
        switch (province_code) {
            case P_LOCATION_CODE:
                final_cost = (int) (DEFAULT_COST * URBAN);
                break;
            default:
                final_cost = (int) (DEFAULT_COST * OUTSKIRTS);
                break;

        }
        return final_cost;
    }
}
