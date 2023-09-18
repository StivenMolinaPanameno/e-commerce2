package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.ReferenceDTO;
import com.project.ecommerce.entities.References;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.service.IReferenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceControllerTest {

    @Mock
    private IReferenceService referenceService;

    @Mock
    BindingResult bindingResult;
    @InjectMocks
    private ReferenceController referenceController;

    @Test
    public void testSave_ReturnsCreatedResponse() throws URISyntaxException, ProductNotFoundException {
        // Configuración de datos de prueba
        Long productId = 1L;
        ReferenceDTO referenceDTO = new ReferenceDTO();

        // Simular el servicio save
        doNothing().when(referenceService).save(referenceDTO, productId);

        // Llamar al método save
        ResponseEntity<?> response = referenceController.save(referenceDTO, productId, bindingResult);

        // Verificar que se haya retornado un ResponseEntity con estado CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/v1/product/reference/1", response.getHeaders().getLocation().getPath());
    }

    @Test
    public void testSave_ReturnsBadRequestResponse() throws URISyntaxException, ProductNotFoundException {
        // Configuración de datos de prueba
        Long productId = 1L;
        ReferenceDTO referenceDTO = new ReferenceDTO();

        // Simular error en la validación (bindingResult tiene errores)
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Llamar al método save con errores de validación
        ResponseEntity<?> response = referenceController.save(referenceDTO, productId, bindingResult);

        // Verificar que se haya retornado un ResponseEntity con estado BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertTrue(responseBody.containsKey("error"));
        assertEquals("Check fields", responseBody.get("error"));
    }

    @Test
    public void testGetReference_ReturnsReferencesList() {
        // Configuración de datos de prueba
        Long productId = 1L;

        List<References> referencesList = Arrays.asList(
                new References(), new References(), new References()
        );

        // Simular el servicio findByProduct_Id
        when(referenceService.findByProduct_Id(productId)).thenReturn(referencesList);

        // Llamar al método getReference
        ResponseEntity<?> response = referenceController.getReference(productId, null);

        // Verificar que se haya retornado un ResponseEntity con estado OK y la lista de referencias
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<References> responseBody = (List<References>) response.getBody();
        assertEquals(referencesList.size(), responseBody.size());
    }

    @Test
    public void testGetReference_ReturnsNotFoundResponse() {
        // Configuración de datos de prueba
        Long productId = 1L;

        // Simular que no se encontraron referencias
        when(referenceService.findByProduct_Id(productId)).thenReturn(Collections.emptyList());

        // Llamar al método getReference
        ResponseEntity<?> response = referenceController.getReference(productId, null);

        // Verificar que se haya retornado un ResponseEntity con estado NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
