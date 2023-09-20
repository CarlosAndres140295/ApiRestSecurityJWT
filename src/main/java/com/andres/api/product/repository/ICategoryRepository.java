package com.andres.api.product.repository;

import com.andres.api.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}
