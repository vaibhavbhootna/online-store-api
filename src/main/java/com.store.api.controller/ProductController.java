package com.store.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ProductController {

    @GetMapping("product/status")
    public String status(){
        return "Service is running " + LocalDate.now();
    }

}