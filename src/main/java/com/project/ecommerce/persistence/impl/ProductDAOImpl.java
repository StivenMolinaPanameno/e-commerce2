package com.project.ecommerce.persistence.impl;

import com.project.ecommerce.entities.Product;
import com.project.ecommerce.persistence.IProductDAO;
import com.project.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDAOImpl implements IProductDAO {

    @Autowired
    ProductRepository repository;
    private String name;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findByCategory(String category) {
        return repository.findByCategoryName(category);
    }

    @Override
    public Optional<Product> findByIdAndEnabledTrueAndCategoryEnabledTrue(Long id) {
        return repository.findByIdAndEnabledTrueAndCategoryEnabledTrue(id);
    }

    @Override
    public Optional<Product> findByNameAndEnabledTrueAndCategoryTrue(String name) {
        return repository.findByNameAndEnabledTrueAndCategoryEnabledTrue(name);
    }
    @Transactional
    @Override
    public void disableById(Long id) {
        repository.disableById(id);
    }
    @Transactional
    @Override
    public void disableByName(String name) {
        repository.disableByName(name);
    }

    @Override
    public void save(Product product) {
        repository.save(product);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Product> findByCategoryNameAndEnabledTrue(String categoryName, Pageable pageable) {
        return repository.findByCategoryNameAndEnabledTrue(categoryName, pageable);
    }

    @Transactional
    @Override
    public Page<Product> findAllByEnabledTrue(Pageable pageable) {
        return repository.findAllByEnabledTrueAndCategoryEnabledTrue(pageable);
    }
}
