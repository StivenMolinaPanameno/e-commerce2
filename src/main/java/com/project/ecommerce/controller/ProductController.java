package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.ProductDTO;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    IProductService service;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Product> productOptional = service.findById(id);
        return productOptional.map(product -> ResponseEntity.ok(convertToDto(product)))
                .orElse(ResponseEntity.badRequest().build());
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) throws URISyntaxException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation Errors");
        }
        service.save(convertToEntity(productDTO));
        return ResponseEntity.created(new URI("/api/v1/products")).build();
    }
    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findName(@PathVariable String name){
        Optional<Product> productOptional = service.findByNameAndEnabledTrue(name);
        return productOptional.map(product -> ResponseEntity.ok(convertToDto(product)))
                .orElse(ResponseEntity.badRequest().build());
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        try {
            List<ProductDTO> productList = service.findAll().stream()
                    .map(this::convertToDto)
                    .toList();
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Devuelve un estado 400 en caso de error
        }
    }

    @GetMapping("/findAll/page/{page}")
    public ResponseEntity<?> findAll(@PathVariable Integer page){
        try {
            Pageable pageable = PageRequest.of(page, 5);

            Page<ProductDTO> productList = service.findAllByEnabledTrue(pageable).map(this::convertToDto);
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build(); // Devuelve un estado 400 en caso de error
        }
    }

    @GetMapping("/findByCategory/{category}/page/{page}")
    public ResponseEntity<?> findByCategory(@PathVariable String category, @PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Page<ProductDTO> productList =  service.findByCategoryName(category, pageable)
                .map(this::convertToDto);
        return ResponseEntity.ok(productList);
    }
    @GetMapping("/findByCategory/{category}")
    public ResponseEntity<?> findByCategory(@PathVariable String category){
        List<ProductDTO> productList =  service.findByCategory(category).stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(productList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Optional<Product> productOptional = service.findById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setCategory(productDTO.getCategory());
            product.setPrice(productDTO.getPrice());
            product.setEnabled(productDTO.getEnabled());
            product.setStock(productDTO.getStock());
            product.setBrand(productDTO.getBrand());

            service.save(product);
            return ResponseEntity.ok(product);
        }
        return  ResponseEntity.notFound().build();
    }

    @PutMapping("/disableById/{id}")
    public ResponseEntity<?> disableById(@PathVariable Long id){
        Optional<Product> productOptional = service.findById(id);
        if(productOptional.isPresent()){
            service.disableById(id);
            return ResponseEntity.ok("Product Disable");
        }
        return ResponseEntity.notFound().build();
    }



    public ProductDTO convertToDto(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .enabled(product.getEnabled())
                .build();
    }
    public Product convertToEntity(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .brand(productDTO.getBrand())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .category(productDTO.getCategory())
                .enabled(productDTO.getEnabled())
                .build();
    }
}
