package com.project.ecommerce.persistence;
import com.project.ecommerce.entities.ProductDetail;

import java.util.Optional;

public interface IProductDetailDAO {
    Optional<ProductDetail> findByProductId(Long id);
    void save(ProductDetail productDetail);
}
