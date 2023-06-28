package com.example.productservice.service;

import com.example.productservice.entity.Product;
import java.util.List;

public interface ProductService {

    //create
    Product create(Product hotel);

    //get all
    List<Product> getAll();

    //get single
    Product get(String id);
}
