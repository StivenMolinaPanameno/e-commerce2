package com.project.ecommerce.controller.DTO;

import com.project.ecommerce.security.entities.UserEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UserEntity user;
    private Date date;
    private BigDecimal total;
    private Boolean enable;
}
