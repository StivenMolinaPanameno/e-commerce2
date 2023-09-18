package com.project.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double weight;
    private String warranty;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
    @Basic(optional = false)
    private Date fabrication;
    @OneToOne(targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
}
