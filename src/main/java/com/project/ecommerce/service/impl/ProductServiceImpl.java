package com.project.ecommerce.service.impl;

import com.project.ecommerce.persistence.IProductDAO;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.ecommerce.entities.Product;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    IProductDAO productDAO;
    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Page<Product> findAllByEnabledTrue(Pageable pageable) {
        return productDAO.findAllByEnabledTrue(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDAO.findByIdAndEnabledTrueAndCategoryEnabledTrue(id);
    }

    @Override
    public Optional<Product> findByNameAndEnabledTrue(String name) {
        return productDAO.findByNameAndEnabledTrueAndCategoryTrue(name);
    }

    @Override
    public void disableById(Long id) {
        productDAO.disableById(id);
    }

    @Override
    public void disableByName(String name) {
        productDAO.disableByName(name);
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public void delete(Long id) {
        productDAO.delete(id);
    }

    @Override
    public Page<Product> findByCategoryName(String categoryName, Pageable pageable) {
        return productDAO.findByCategoryNameAndEnabledTrue(categoryName, pageable);
    }
}
