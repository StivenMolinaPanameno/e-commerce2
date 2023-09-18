package com.project.ecommerce.persistence;



import com.project.ecommerce.entities.References;

import java.util.List;


public interface IReferenceDAO {
    List<References> findAll();
    void save(References ref);
    List<References> findByProduct_Id(Long id);
}
