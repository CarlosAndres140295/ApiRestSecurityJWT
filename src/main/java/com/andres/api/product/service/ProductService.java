package com.andres.api.product.service;

import com.andres.api.product.dto.ProductRequestDTO;
import com.andres.api.product.dto.ProductResponseDTO;
import com.andres.api.product.model.Category;
import com.andres.api.product.model.Product;
import com.andres.api.product.repository.ICategoryRepository;
import com.andres.api.product.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ModelMapper modelMapper;

    private final IProductRepository productRepository;

    private final ICategoryRepository categoryRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO request){

        Optional<Category> category = categoryRepository.findByName(request.getCategory());
        if (category.isEmpty()){
            throw  new IllegalArgumentException("Category not found");
        }

        Product product = modelMapper.map(request, Product.class);

        product = productRepository.save(product);

        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
