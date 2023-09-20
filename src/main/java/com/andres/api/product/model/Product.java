package com.andres.api.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @Column(nullable = false, name = "prod_name")
    private String name;

    @Column(name = "prod_description")
    private String description;

    @Column(nullable = false, name = "prod_price")
    private Double price;

    @Column(name = "prod_image")
    private String image;

    @Column(nullable = false, name = "prod_active")
    private Boolean active;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Category category;


}
