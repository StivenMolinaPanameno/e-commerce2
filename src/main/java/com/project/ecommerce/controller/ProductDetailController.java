package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.ProductDetailDTO;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.ProductDetail;
import com.project.ecommerce.service.IProductDetailService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductDetailController {
    @Autowired
    IProductDetailService service;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveDetail")
    public ResponseEntity<?> save(
            @Valid @RequestBody ProductDetailDTO productDetailDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Check that the fields are valid");
        }
        Product product = Product.builder()
                .name(productDetailDTO.getName())
                .stock(productDetailDTO.getStock())
                .price(productDetailDTO.getPrice())
                .brand(productDetailDTO.getBrand())
                .category(productDetailDTO.getCategory())
                .enabled(true)
                .build();

        ProductDetail productDetail = ProductDetail.builder()
                .description(productDetailDTO.getDescription())
                .weight(productDetailDTO.getWeight())
                .warranty(productDetailDTO.getWarranty())
                .fabrication(productDetailDTO.getFabrication())
                .product(product)
                .build();

        service.saveProductAndDetail(productDetail, product);

        return ResponseEntity.ok("Product and Detail created successfully");


    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/findDetail/{id}")
    public ResponseEntity<?> findDetailProduct(@PathVariable Long id){
        Optional<ProductDetail> detail = service.findByProductId(id);
        if(detail.isPresent()){
            return ResponseEntity.ok(detail);
        }
        return ResponseEntity.notFound().build();
    }
}
