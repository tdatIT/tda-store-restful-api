package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.data.entities.UserAddress;
import com.webapp.tdastore.data.exception.CustomExceptionRuntime;
import com.webapp.tdastore.data.payload.response.ShippingResponse;
import com.webapp.tdastore.data.repositories.UserAddressRepo;
import com.webapp.tdastore.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/shipping-cost-calculator")
public class ShippingRestController {
    @Autowired
    private UserAddressRepo userAddressRepo;
    @Autowired
    private ShippingService shippingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ShippingResponse> calculateShipping(@RequestParam long addressId) {
        UserAddress address = userAddressRepo.findById(addressId).orElseThrow();
        if (address == null) {
            throw new CustomExceptionRuntime(400, "Address not found");
        }
        int cost = shippingService.getPriceFromUserAddress(address.getProvince(), address.getDistrict());
        return ResponseEntity.status(HttpStatus.OK).body(new ShippingResponse(
                200,
                "Calculate success from store to :" + address.getAPIName(),
                cost));
    }

    @RequestMapping(value = "/new-address", method = RequestMethod.GET)
    public ResponseEntity<ShippingResponse> calculateShippingNewAddress(@RequestParam Integer provinceCode,
                                                                        @RequestParam Integer districtCode) {
        int cost = shippingService.getPriceFromUserAddress(provinceCode, districtCode);
        return ResponseEntity.status(HttpStatus.OK).body(new ShippingResponse(
                200,
                "Calculate cost success",
                cost
        ));
    }
}
