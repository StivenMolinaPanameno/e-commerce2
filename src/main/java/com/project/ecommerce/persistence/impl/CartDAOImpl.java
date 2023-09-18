package com.project.ecommerce.persistence.impl;

import com.project.ecommerce.entities.Cart;
import com.project.ecommerce.persistence.ICartDAO;
import com.project.ecommerce.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Transactional
@Component
public class CartDAOImpl implements ICartDAO {
    @Autowired
    CartRepository cartRepository;

    @Override
    public List<Cart> findByUserEntityUsername(String name) {
        return cartRepository.findByUser_Username(name);
    }

    @Override
    public List<Cart> findByUserEntityId(Long id) {
        return cartRepository.findByUser_Id(id);
    }

    @Override
    public void cleanCartByUserId(Long id) {
        cartRepository.deleteByUser_Id(id);
    }

    @Override
    public Long countByUserEntityId(Long id) {
        return cartRepository.countByUser_Id(id);
    }

    @Override
    public void addProduct(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteCartById(Long id) {
        cartRepository.deleteById(id);
    }
}
