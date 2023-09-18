package com.project.ecommerce.repository;


import com.project.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Username(String username);
    Optional<Order> findFirstByUserUsernameOrderByDateDesc(String username);

    @Query("SELECT o FROM Order o WHERE o.user.username = :username AND o.enable = true ORDER BY o.date DESC")
    Optional<Order> findLastActiveOrderByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE Order o SET o.enable = false WHERE o.id = :orderId")
    void disableOrderById(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o WHERE o.user.username = :username AND o.enable = true ORDER BY o.date DESC")
    List<Order> findActiveOrdersByUsername(@Param("username") String username);


}
