package com.project.ecommerce.repository;


import com.project.ecommerce.entities.References;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<References, Long> {
    List<References> findByProduct_Id(Long id);
}
