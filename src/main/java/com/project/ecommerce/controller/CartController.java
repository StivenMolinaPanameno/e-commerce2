package com.project.ecommerce.controller;


import com.project.ecommerce.controller.DTO.CartDTO;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.service.ICartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    ICartService cartService;


    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping()
    public ResponseEntity<?> getListByClient(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cartService.findByUserEntityUsername(username), HttpStatus.OK);
    }
    @GetMapping("/count/{id}")
    public ResponseEntity<Long> countByUser(@PathVariable Long id){
        return new ResponseEntity<>(cartService.countByUserEntityId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProdudct(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult) throws UserNotFoundException {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid Fields");
        }
        cartService.addProduct(cartDTO);
        return ResponseEntity.ok("Product added");

    }
    @DeleteMapping("/clean/{item_id}")
    public ResponseEntity<?> removeProduct(@PathVariable("item_id")Long id) throws Exception {
        cartService.deleteCartById(id);
        return  ResponseEntity.ok("Products Deleted");
    }



}
