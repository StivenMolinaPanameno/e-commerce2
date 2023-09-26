package com.project.ecommerce.security.controller;

import com.project.ecommerce.security.controller.dto.CreateUserDTO;
import com.project.ecommerce.security.entities.ERole;
import com.project.ecommerce.security.entities.RoleEntity;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.RoleRepository;
import com.project.ecommerce.security.service.IUserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {
    @Autowired
    IUserService userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/hello")
    public String hello(){
        return "Hello World Not Secured";
    }

@Autowired
    RoleRepository roleRepository;
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> {
                    ERole roleEnum = ERole.valueOf(role);
                    return roleRepository.findByName(roleEnum)
                            .orElseGet(() -> RoleEntity.builder().name(roleEnum).build());
                })
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .id(createUserDTO.getId())
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .name(createUserDTO.getName())
                .enabled(true)
                .roles(roles)
                .build();

        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted");
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/disableByUsername/{username}")
    public ResponseEntity<?> disableUsername(@PathVariable String username){
        try{
            userRepository.disableByUsername(username);
            return ResponseEntity.ok("User disable successful");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("User Not Found");
        }
    }






}
