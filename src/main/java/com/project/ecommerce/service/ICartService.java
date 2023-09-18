package com.project.ecommerce.service;



import com.project.ecommerce.controller.DTO.CartDTO;
import com.project.ecommerce.entities.Cart;
import com.project.ecommerce.exception.UserNotFoundException;

import java.util.List;

public interface ICartService {
    List<Cart> findByUserEntityUsername(String name);
    List<Cart> findByUserEntityId(Long id);
    void cleanCartByUserId(Long id);
    Long countByUserEntityId(Long id);
    void addProduct(CartDTO cart) throws UserNotFoundException;
    void deleteCartById(Long id);
}
