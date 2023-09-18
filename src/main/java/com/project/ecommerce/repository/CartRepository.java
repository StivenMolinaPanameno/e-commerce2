package com.project.ecommerce.repository;


import com.project.ecommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_Username(String name);
    List<Cart> findByUser_Id(Long id);
    void deleteByUser_Id(Long id);
    Long countByUser_Id(Long id);
}
