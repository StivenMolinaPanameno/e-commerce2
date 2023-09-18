package com.project.ecommerce.service.impl;

import com.project.ecommerce.entities.LikeDislike;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.persistence.LikeDislikeDAO;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.persistence.IUserRepositoryDAO;

import com.project.ecommerce.service.ILikeDislikeService;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeDislikeServiceImpl implements ILikeDislikeService {
    @Autowired
    LikeDislikeDAO likeDislikeDAO;
    @Autowired
    IUserRepositoryDAO userRepository;
    @Autowired
    IProductService productService;



    @Override
    public void save(String vote, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            Long idUser = user.getId();

            Optional<Product> productOptional = productService.findById(id);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                handleLikeDislike(user, idUser, product, id, vote);
            }
        }
    }

    public void handleLikeDislike(UserEntity user, Long idUser, Product product, Long idProduct, String vote) {
        boolean existUser = likeDislikeDAO.existsByUserId(idUser);
        boolean existProduct = likeDislikeDAO.existsByProductId(idProduct);

        if (existUser && existProduct) {
            Optional<LikeDislike> likeDislikeOptional = likeDislikeDAO.findByUser_IdAndProduct_Id(idUser, idProduct);

            likeDislikeOptional.ifPresent(likeDislike -> {
                LikeDislike likeOrDislike = LikeDislike.builder()
                        .id(likeDislike.getId())
                        .product(likeDislike.getProduct())
                        .user(user)
                        .vote(vote)
                        .build();
                likeDislikeDAO.save(likeOrDislike);
            });

        } else {
            LikeDislike likeDislike = LikeDislike.builder()
                    .vote(vote)
                    .user(user)
                    .product(product)
                    .build();
            likeDislikeDAO.save(likeDislike);
        }
    }
     /*@Override
    public void save(String vote, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        UserEntity user = userOptional.get();

        Long idUser = user.getId();

        Optional<Product> productOptional = productService.findById(id);

        boolean existUser = likeDislikeDAO.existsByUserId(idUser);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            Long idProduct = product.getId();

            boolean existProduct;

            existProduct = likeDislikeDAO.existsByProductId(idProduct);

            if(existProduct && existUser){
                Optional<LikeDislike> likeDislikeOptional = likeDislikeDAO.findByUser_IdAndProduct_Id(idUser, idProduct);
                if(likeDislikeOptional.isPresent()){
                    LikeDislike likeDislike = likeDislikeOptional.get();
                    LikeDislike likeOrDislike = LikeDislike.builder()
                            .id(likeDislike.getId())
                            .product(likeDislike.getProduct())
                            .user(user)
                            .vote(vote)
                            .build();
                    likeDislikeDAO.save(likeOrDislike);
                }

            }else{
                LikeDislike likeDislike = LikeDislike.builder()
                        .vote(vote)
                        .user(user)
                        .product(product)
                        .build();
                likeDislikeDAO.save(likeDislike);
            }
        }


    }*/

}
