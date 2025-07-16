package com.cqrs.command.api.controller;

import com.cqrs.command.api.model.ProductRestModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @PostMapping
    public String adddProduct(@RequestBody ProductRestModel productRestModel){
        return "Product added";
    }
}
