package com.andres.api.product.controller;

import com.andres.api.product.dto.ProductRequestDTO;
import com.andres.api.product.dto.ProductResponseDTO;
import com.andres.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO request){
        return service.createProduct(request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
    }
}
