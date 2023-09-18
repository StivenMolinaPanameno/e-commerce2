package com.project.ecommerce.repository;


import com.project.ecommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);
    Page<Product> findByCategoryNameAndEnabledTrue(String categoryName, Pageable pageable);
    Page<Product> findAllByEnabledTrueAndCategoryEnabledTrue(Pageable pageable);
    Optional<Product> findByNameAndEnabledTrueAndCategoryEnabledTrue(String nameProduct);
    Optional<Product> findByIdAndEnabledTrueAndCategoryEnabledTrue(Long id);

    @Modifying
    @Query("UPDATE Product u SET u.enabled = false WHERE u.id = :productId")
    void disableById(@Param("productId") Long id);
    @Modifying
    @Query("UPDATE Product u SET u.enabled = false WHERE u.name = :productName")
    void disableByName(@Param("productName") String productName);
}
