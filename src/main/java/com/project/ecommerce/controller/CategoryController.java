package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.CategoryDTO;
import com.project.ecommerce.entities.Category;
import com.project.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        if(categoryDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        categoryService.save(Category.builder()
                .name(categoryDTO.getName())
                .enabled(categoryDTO.getEnabled())
                .build());
        return  ResponseEntity.created(new URI("api/maker/save")).build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if(categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            category.setName(categoryDTO.getName());
            category.setEnabled(categoryDTO.getEnabled());
            categoryService.save(category);
            return ResponseEntity.ok("Update Successful");
        }
        return ResponseEntity.notFound().build();

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/disableByBody/{id}")
    public ResponseEntity<?> updateAttribute(@PathVariable Long id, @RequestBody boolean newValue) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setEnabled(newValue);
            categoryService.save(category);

            return ResponseEntity.ok("Successful");
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<?> enabled(@PathVariable Long id){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if(categoryOptional.isPresent()){
            categoryService.disableById(id);
            return ResponseEntity.ok("Category Disable");
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/disableByName/{category}")
    public ResponseEntity<?> enabled(@PathVariable String category){
        Optional<Category> categoryOptional = categoryService.findByName(category);
        if(categoryOptional.isPresent()){
            categoryService.disableByName(category);
            return ResponseEntity.ok("Category Disable");
        }
        return ResponseEntity.notFound().build();
    }

}
