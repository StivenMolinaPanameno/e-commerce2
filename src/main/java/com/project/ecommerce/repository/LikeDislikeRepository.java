package com.project.ecommerce.repository;
import com.project.ecommerce.entities.LikeDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeDislikeRepository extends JpaRepository<LikeDislike, Long> {
    boolean existsByUser_Id(Long userId);
    boolean existsByProduct_Id(Long id);
    @Modifying
    @Query("UPDATE LikeDislike l SET l.vote = ?1 WHERE l.user.id = ?2 AND l.product.id = ?3")
    void updateLikeDislike(String vote, Long user, Long product);
    Optional<LikeDislike> findByUser_IdAndProduct_Id(Long idUser, Long idProduct);

}
