package com.project.ecommerce.service;


import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.ProductDetail;

import java.util.Optional;

public interface IProductDetailService {
    Optional<ProductDetail> findByProductId(Long id);
    void save(ProductDetail productDetail);
    void saveProductAndDetail(ProductDetail detail, Product product);
}
