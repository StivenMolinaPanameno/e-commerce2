package com.project.ecommerce.security.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private Long id;
    @NotBlank
    private String name;

    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Set<String> roles;
}
