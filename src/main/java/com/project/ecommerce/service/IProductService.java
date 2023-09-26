package com.project.ecommerce.service;



import com.project.ecommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    Page<Product> findAllByEnabledTrue(Pageable pageable);

    Optional<Product> findById(Long id);
    Optional<Product> findByNameAndEnabledTrue(String nameProduct);
    void disableById(Long id);
    void disableByName(String name);
    void save(Product product);
    void delete(Long id);
    Page<Product> findByCategoryName(String categoryName, Pageable pageable);


}
