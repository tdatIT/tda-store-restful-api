package com.webapp.tdastore.controller.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderRestController {
    @GetMapping("/test")
    public String randomStuff() {
        return "JWT Hợp lệ mới có thể thấy được message này";
    }
}
