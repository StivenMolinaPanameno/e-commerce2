package com.project.ecommerce.controller.DTO;

import com.project.ecommerce.entities.Category;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private String name;
    private Integer stock;
    private BigDecimal price;
    private String brand;
    private String description;
    private Double weight;
    private String warranty;
    private Date fabrication;
    private Category category;

}
