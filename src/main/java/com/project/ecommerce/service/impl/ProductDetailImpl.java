package com.project.ecommerce.service.impl;

import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.ProductDetail;
import com.project.ecommerce.persistence.IProductDetailDAO;
import com.project.ecommerce.service.IProductDetailService;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailImpl implements IProductDetailService {
    @Autowired
    IProductDetailDAO productDetailDAO;
    @Autowired
    IProductService productService;
    @Override
    public Optional<ProductDetail> findByProductId(Long id) {
        return productDetailDAO.findByProductId(id);
    }

    @Override
    public void save(ProductDetail productDetail) {
        productDetailDAO.save(productDetail);
    }

    @Override
    public void saveProductAndDetail(ProductDetail detail, Product product) {
        productService.save(product);
        productDetailDAO.save(detail);
    }
}
