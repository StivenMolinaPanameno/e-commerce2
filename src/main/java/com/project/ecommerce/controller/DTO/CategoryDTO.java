package com.project.ecommerce.controller.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private String name;

    private Boolean enabled;

}
