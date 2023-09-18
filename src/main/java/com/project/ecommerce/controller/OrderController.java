package com.project.ecommerce.controller;


import com.project.ecommerce.entities.Order;
import com.project.ecommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    IOrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createOrder(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        orderService.createOrder(username);
        return ResponseEntity.ok("Order Created");
    }

    @GetMapping("/lastOrder")
    public ResponseEntity<?> getLastOrder(){

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Order> order = orderService.findFirstByUserUsernameOrderByDateDesc(username);
            if(order.isPresent()){
                return  ResponseEntity.ok(order);
            }

            return ResponseEntity.badRequest().body("Not found");

    }

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
            orderService.disableOrderById(id);
            return ResponseEntity.ok("Order Canceled");

    }
}
