package com.project.ecommerce.persistence;



import com.project.ecommerce.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoryDAO {
    List<Category> findAll();

    Optional<Category> findById(Long id);
    void save(Category category);
    void disableById(Long id);
    void disableByName(String name);
    Optional<Category> findByName(String name);


}
