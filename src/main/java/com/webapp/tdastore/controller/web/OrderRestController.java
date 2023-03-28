package com.webapp.tdastore.controller.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
    @GetMapping("/test")
    public String randomStuff() {
        return "JWT Hợp lệ mới có thể thấy được message này";
    }
}
