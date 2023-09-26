package com.project.ecommerce.controller;


import com.project.ecommerce.controller.DTO.ProductDTO;
import com.project.ecommerce.entities.Order;
import com.project.ecommerce.exception.NotProducts;
import com.project.ecommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(path = "/create")
    public ResponseEntity<?> createOrder() throws NotProducts {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            orderService.createOrder(username);
            return ResponseEntity.ok("Order Created");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new NotProducts("Add Products"));
        }

    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/lastOrder")
    public ResponseEntity<?> getLastOrder(){

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Order> order = orderService.findFirstByUserUsernameOrderByDateDesc(username);
            if(order.isPresent()){
                return  ResponseEntity.ok(order);
            }

            return ResponseEntity.badRequest().body("Not found");

    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/allMyOrders")
    public ResponseEntity<?> getAllMyOrders(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> order = orderService.findActiveOrdersByUsername(username);
        if(order.contains(null)){
            return ResponseEntity.badRequest().body("Not found");
        }
        return ResponseEntity.ok(order);


    }

    @PutMapping("/disableOrder/{id}")
    public ResponseEntity<?> disableOrder(@PathVariable Long id){
        try{
            orderService.disableOrderById(id);
            return ResponseEntity.ok("Order Canceled");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Product Not Found");
        }


    }
    @GetMapping("/findAll/page/{page}")
    public ResponseEntity<?> findAll(@PathVariable Integer page){
        try{
            Pageable pageable = PageRequest.of(page, 5);
            Page<Order> orderList = orderService.findAll(pageable);
            return ResponseEntity.ok(orderList);
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }


}
