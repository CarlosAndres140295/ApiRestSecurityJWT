package com.andres.api.product.service;

import com.andres.api.product.dto.ProductRequestDTO;
import com.andres.api.product.dto.ProductResponseDTO;
import com.andres.api.product.model.Category;
import com.andres.api.product.model.Product;
import com.andres.api.product.repository.ICategoryRepository;
import com.andres.api.product.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;

    private final ICategoryRepository categoryRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO request){

        Optional<Category> category = categoryRepository.findByName(request.getCategory());
        if (category.isEmpty()){
            throw  new IllegalArgumentException("Category not found");
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setActive(true);
        product.setImage(request.getImage());
        product.setCategory(category.get());

        product = productRepository.save(product);

        return new ProductResponseDTO(product.getId(),
                                        product.getName(),
                                        product.getDescription(),
                                        product.getPrice(),
                                        product.getImage(),
                                        product.getActive(),
                                        product.getCategory().getName());
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
