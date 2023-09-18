package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.LikeDislike;

import java.util.Optional;

public interface LikeDislikeDAO {
    void save(LikeDislike likeDislike);
    boolean existsByProductId(Long id);
    boolean existsByUserId(Long id);
    void update(String vote, Long idUser, Long idProduct);
    Optional<LikeDislike> findByUser_IdAndProduct_Id(Long idUser, Long idProduct);

}
