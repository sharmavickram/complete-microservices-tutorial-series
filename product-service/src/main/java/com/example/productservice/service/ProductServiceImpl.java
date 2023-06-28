package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        String ProductId = UUID.randomUUID().toString();
        product.setId(ProductId);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product get(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with given id not found !!"));
    }
}
