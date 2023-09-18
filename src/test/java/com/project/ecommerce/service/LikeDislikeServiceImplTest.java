package com.project.ecommerce.service;

import com.project.ecommerce.entities.LikeDislike;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.persistence.LikeDislikeDAO;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.persistence.IUserRepositoryDAO;
import com.project.ecommerce.service.impl.LikeDislikeServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LikeDislikeServiceImplTest {
    @Mock
    private SecurityContextHolder securityContextHolder;

    @Mock
    private IUserRepositoryDAO userRepository;

    @Mock
    private IProductService productService;

    @Mock
    private LikeDislikeDAO likeDislikeDAO;

    @InjectMocks
    private LikeDislikeServiceImpl likeDislikeService;

    @Test
    public void testSave() {
        // Configurar datos de prueba
        String username = "testuser";
        Long userId = 1L;
        Long productId = 2L;
        String vote = "like";

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        Product product = new Product();
        product.setId(productId);

        // Configurar mock de SecurityContextHolder
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Configurar mock de userRepository
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        // Configurar mock de productService
        when(productService.findById(productId)).thenReturn(Optional.of(product));

        // Llamar al método que queremos probar
        likeDislikeService.save(vote, productId);

        // Verificar que los métodos de DAO fueron llamados correctamente
        verify(userRepository).findByUsername(username);
        verify(productService).findById(productId);

        // No es necesario verificar llamadas a likeDislikeDAO en este método
    }

    @Test
    public void testHandleLikeDislike_UserAndProductExist() {
        // Configuración de datos de prueba
        Long userId = 1L;
        Long productId = 2L;
        String vote = "like";

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        Product product = new Product();

        when(likeDislikeDAO.existsByUserId(userId)).thenReturn(true);
        when(likeDislikeDAO.existsByProductId(productId)).thenReturn(true);
        when(likeDislikeDAO.findByUser_IdAndProduct_Id(userId, productId)).thenReturn(Optional.of(new LikeDislike()));

        // Llamar al método que queremos probar
        likeDislikeService.handleLikeDislike(userEntity, userId, product, productId, vote);

        // Verificar que el método de DAO fue llamado correctamente
        verify(likeDislikeDAO).findByUser_IdAndProduct_Id(userId, productId);

        // Verificar que el método de DAO save fue llamado correctamente con el voto actualizado
        ArgumentCaptor<LikeDislike> likeDislikeCaptor = ArgumentCaptor.forClass(LikeDislike.class);
        verify(likeDislikeDAO).save(likeDislikeCaptor.capture());

        LikeDislike likeDislike = likeDislikeCaptor.getValue();
        assertEquals(vote, likeDislike.getVote());
    }
}
