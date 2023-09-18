package com.project.ecommerce.persistence;



import com.project.ecommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductDAO {
    List<Product> findAll();
    List<Product> findByCategory(String category);

    Optional<Product> findByIdAndEnabledTrueAndCategoryEnabledTrue(Long id);
    Optional<Product> findByNameAndEnabledTrueAndCategoryTrue(String nameProduct);
    void disableById(Long id);
    void disableByName(String name);
    void save(Product product);
    void delete(Long id);
    Page<Product> findByCategoryNameAndEnabledTrue(String categoryName, Pageable pageable);
    Page<Product> findAllByEnabledTrue(Pageable pageable);
}
