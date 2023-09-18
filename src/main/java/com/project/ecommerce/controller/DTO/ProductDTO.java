package com.project.ecommerce.controller.DTO;
import com.project.ecommerce.entities.Category;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NonNull
    private String name;
    @NonNull
    private Integer stock;
    @NonNull
    private BigDecimal price;
    @NotNull
    private String brand;
    @NotNull
    private Category category;
    @NotNull
    private Boolean enabled;
}
