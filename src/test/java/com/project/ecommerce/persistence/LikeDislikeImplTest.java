package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.LikeDislike;
import com.project.ecommerce.persistence.impl.LikeDislikeDAOImpl;
import com.project.ecommerce.repository.LikeDislikeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LikeDislikeImplTest {

    @Mock
    private LikeDislikeRepository mockLikeDislikeRepository;

    @InjectMocks
    private LikeDislikeDAOImpl likeDislikeDAO;

   @Test
    public void saveTest() {
        LikeDislike likeDislikeToSave = new LikeDislike();

        likeDislikeDAO.save(likeDislikeToSave);

        verify(mockLikeDislikeRepository).save(likeDislikeToSave);
    }

    @Test
    public void existsByProductIdTest() {
        Long productId = 1L;
        when(mockLikeDislikeRepository.existsByProduct_Id(productId)).thenReturn(true);

        boolean result = likeDislikeDAO.existsByProductId(productId);

        assertTrue(result);
    }

    @Test
    public void existsByUserIdTest() {
        Long userId = 1L;
        when(mockLikeDislikeRepository.existsByUser_Id(userId)).thenReturn(true);

        boolean result = likeDislikeDAO.existsByUserId(userId);

        assertTrue(result);
    }

    @Test
    public void updateTest() {
        String vote = "like";
        Long userId = 1L;
        Long productId = 1L;

        likeDislikeDAO.update(vote, userId, productId);

        verify(mockLikeDislikeRepository).updateLikeDislike(vote, userId, productId);
    }

    @Test
    public void findByUser_IdAndProduct_IdTest() {
        Long userId = 1L;
        Long productId = 1L;
        LikeDislike expectedLikeDislike = new LikeDislike();
        when(mockLikeDislikeRepository.findByUser_IdAndProduct_Id(userId, productId)).thenReturn(Optional.of(expectedLikeDislike));

        Optional<LikeDislike> result = likeDislikeDAO.findByUser_IdAndProduct_Id(userId, productId);

        assertTrue(result.isPresent());
        assertEquals(expectedLikeDislike, result.get());
    }
}