package com.andres.api.product.dto;

import com.andres.api.product.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private String image;
    private Boolean active;
    private String category;
}
