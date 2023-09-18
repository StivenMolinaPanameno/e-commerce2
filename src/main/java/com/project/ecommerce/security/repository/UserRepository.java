package com.project.ecommerce.security.repository;

import com.project.ecommerce.security.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String name);
    @Modifying
    @Query("UPDATE UserEntity u SET u.enabled = false WHERE u.username = :username")
    void disableByName(@Param("username") String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.enabled = false WHERE u.id = :id")
    void disableById(@Param("id") Long id);

}
