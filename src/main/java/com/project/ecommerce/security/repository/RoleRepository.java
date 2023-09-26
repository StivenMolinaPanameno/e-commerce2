package com.project.ecommerce.security.repository;


import com.project.ecommerce.security.entities.ERole;
import com.project.ecommerce.security.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole roleEnum);
}
