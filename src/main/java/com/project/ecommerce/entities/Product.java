package com.project.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    private Integer stock;
    private BigDecimal price;
    private String brand;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;
    @JsonIgnore
    private Boolean enabled;
    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private ProductDetail productDetail;
}
