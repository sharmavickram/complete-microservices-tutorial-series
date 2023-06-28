package com.example.userservice.external_services;

import com.example.userservice.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductService {
    @GetMapping("/products/{productId}")
    Product getProduct(@PathVariable String productId);
}
