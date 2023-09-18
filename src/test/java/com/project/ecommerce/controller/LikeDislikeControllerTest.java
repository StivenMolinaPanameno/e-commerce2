package com.project.ecommerce.controller;

import com.project.ecommerce.service.ILikeDislikeService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LikeDislikeControllerTest {

    @Mock
    private ILikeDislikeService likeDislikeService;

    @InjectMocks
    private LikeDislikeController controller;



    @Test
    public void testLikeOrDislike() {
        Long id = 1L;
        String likeOrDislike = "like";

        // Configurar el comportamiento del servicio mock
        doNothing().when(likeDislikeService).save(likeOrDislike, id);

        // Llamar al método del controlador
        ResponseEntity<?> responseEntity = controller.likeOrDislike(id, likeOrDislike);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(" ", responseEntity.getBody());

        // Verificar que el servicio haya sido llamado
        verify(likeDislikeService).save(likeOrDislike, id);
    }

    @Test
    public void testInvalidLikeOrDislike() {
        Long id = 1L;
        String likeOrDislike = "invalid";

        // Llamar al método del controlador
        ResponseEntity<?> responseEntity = controller.likeOrDislike(id, likeOrDislike);

        // Verificar el resultado
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
