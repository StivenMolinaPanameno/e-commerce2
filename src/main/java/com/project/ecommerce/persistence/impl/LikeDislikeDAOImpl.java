package com.project.ecommerce.persistence.impl;


import com.project.ecommerce.entities.LikeDislike;
import com.project.ecommerce.persistence.LikeDislikeDAO;
import com.project.ecommerce.repository.LikeDislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LikeDislikeDAOImpl implements LikeDislikeDAO {
    @Autowired
    LikeDislikeRepository likeDislikeRepository;

    @Override
    public void save(LikeDislike likeDislike) {
        likeDislikeRepository.save(likeDislike);
    }

    @Override
    public boolean existsByProductId(Long id) {
        return likeDislikeRepository.existsByProduct_Id(id);
    }

    @Override
    public boolean existsByUserId(Long id) {
        return likeDislikeRepository.existsByUser_Id(id);
    }

    @Override
    public void update(String vote, Long idUser, Long idProduct) {
        likeDislikeRepository.updateLikeDislike(vote, idUser, idProduct);
    }

    @Override
    public Optional<LikeDislike> findByUser_IdAndProduct_Id(Long idUser, Long idProduct) {
        return likeDislikeRepository.findByUser_IdAndProduct_Id(idUser,idProduct);
    }

}
