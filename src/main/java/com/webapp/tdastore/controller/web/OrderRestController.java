package com.webapp.tdastore.controller.web;

import com.webapp.tdastore.data.entities.Order;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderRestController {

}
