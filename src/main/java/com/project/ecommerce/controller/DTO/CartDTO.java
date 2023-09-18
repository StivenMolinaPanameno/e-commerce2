package com.project.ecommerce.controller.DTO;

import com.project.ecommerce.entities.Product;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Product product;
    private Integer amount;
}
