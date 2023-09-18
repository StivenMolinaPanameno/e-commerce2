package com.project.ecommerce.security.persistence;

import com.project.ecommerce.security.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserRepositoryDAO {
    void save(UserEntity user);
    List<UserEntity> findAll();
    void disableById(Long id);
    void disableByUsername(String username);
    void deleteById(Long id);
    Optional<UserEntity> findByUsername(String username);
}
