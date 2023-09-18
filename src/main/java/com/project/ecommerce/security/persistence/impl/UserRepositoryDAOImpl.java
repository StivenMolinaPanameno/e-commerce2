package com.project.ecommerce.security.persistence.impl;

import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.persistence.IUserRepositoryDAO;
import com.project.ecommerce.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryDAOImpl implements IUserRepositoryDAO {
    @Autowired
    UserRepository userRepository;

    public void save(UserEntity user){
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void disableById(Long id) {
        userRepository.disableById(id);
    }

    @Override
    public void disableByUsername(String username) {
        userRepository.disableByName(username);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
