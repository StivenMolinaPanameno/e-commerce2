package com.project.ecommerce.persistence.impl;

import com.project.ecommerce.entities.References;
import com.project.ecommerce.persistence.IReferenceDAO;
import com.project.ecommerce.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReferenceDAOImpl implements IReferenceDAO {
    @Autowired
    ReferenceRepository referenceRepository;



    @Override
    public List<References> findAll() {
        return referenceRepository.findAll();
    }

    @Override
    public void save(References ref) {
        referenceRepository.save(ref);
    }

    @Override
    public List<References> findByProduct_Id(Long id) {
        return referenceRepository.findByProduct_Id(id);
    }
}
