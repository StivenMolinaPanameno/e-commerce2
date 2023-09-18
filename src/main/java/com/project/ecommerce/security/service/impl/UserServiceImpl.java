package com.project.ecommerce.security.service.impl;

import com.project.ecommerce.security.persistence.IUserRepositoryDAO;
import com.project.ecommerce.security.service.IUserService;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepositoryDAO repository;
    @Override
    public void save(UserEntity user) {
        repository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void disableById(Long id) {
        repository.disableById(id);
    }

    @Override
    public void disableByUsername(String username) {
        repository.disableByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }


}
