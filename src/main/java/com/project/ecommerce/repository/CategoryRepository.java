package com.project.ecommerce.repository;


import com.project.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Query("UPDATE Category c SET c.enabled = false WHERE c.id = :categoryId")
    void disableCategoryById(@Param("categoryId")Long categoryId);

    @Modifying
    @Query("UPDATE Category c SET c.enabled = false WHERE c.name = :name")
    void disableCategoryByName(@Param("name")String name);

    Optional<Category> findByName(String name);
}
