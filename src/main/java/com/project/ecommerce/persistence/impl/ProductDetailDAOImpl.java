package com.project.ecommerce.persistence.impl;


import com.project.ecommerce.entities.ProductDetail;
import com.project.ecommerce.persistence.IProductDetailDAO;
import com.project.ecommerce.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductDetailDAOImpl implements IProductDetailDAO {
    @Autowired
    ProductDetailRepository repository;
    @Override
    public Optional<ProductDetail> findByProductId(Long id) {
        return repository.findByProduct_Id(id);
    }

    @Override
    public void save(ProductDetail productDetail) {
        repository.save(productDetail);
    }
}
