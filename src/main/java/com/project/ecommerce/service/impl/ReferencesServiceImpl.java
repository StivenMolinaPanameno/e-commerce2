package com.project.ecommerce.service.impl;


import com.project.ecommerce.controller.DTO.ReferenceDTO;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.References;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.persistence.IReferenceDAO;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReferencesServiceImpl implements IReferenceService {

    @Autowired
    IReferenceDAO referenceDAO;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IProductService productService;
    @Override
    public List<References> findAll() {
        return referenceDAO.findAll();
    }

    @Override
    public void save(ReferenceDTO references, Long id) throws ProductNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> user = userRepository.findByUsername(username);
        Optional<Product> productOptional = productService.findById(id);
        if (user.isPresent() && productOptional.isPresent()) {
            UserEntity userEntity = user.get();
            Product product = productOptional.get();
            References reference = References.builder()
                    .reference(references.getReference())
                    .product(product)
                    .user(userEntity)
                    .build();
            referenceDAO.save(reference);
        } else {
            throw new ProductNotFoundException("Product not found"); // Lanza una excepción en lugar de devolver un ResponseEntity
        }
    }

    @Override
    public List<References> findByProduct_Id(Long id) {
        return referenceDAO.findByProduct_Id(id);
    }
}
