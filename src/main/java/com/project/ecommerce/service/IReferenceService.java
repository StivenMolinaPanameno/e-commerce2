package com.project.ecommerce.service;


import com.project.ecommerce.controller.DTO.ReferenceDTO;
import com.project.ecommerce.entities.References;
import com.project.ecommerce.exception.ProductNotFoundException;

import java.util.List;

public interface IReferenceService {
    List<References> findAll();
    void save(ReferenceDTO references, Long id) throws ProductNotFoundException;
    List<References> findByProduct_Id(Long id);
}
