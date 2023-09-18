package com.project.ecommerce.controller.DTO;

import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceDTO {
    @NonNull
    private String reference;
}
