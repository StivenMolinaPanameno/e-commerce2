package com.project.ecommerce.persistence;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.project.ecommerce.entities.References;
import com.project.ecommerce.persistence.impl.ReferenceDAOImpl;
import com.project.ecommerce.repository.ReferenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceDAOImplTest {

    @Mock
    private ReferenceRepository mockReferenceRepository;

    @InjectMocks
    private ReferenceDAOImpl referenceDAO;

    @Test
    public void findAllTest() {
        List<References> expectedReferences = Arrays.asList(new References(), new References());
        when(mockReferenceRepository.findAll()).thenReturn(expectedReferences);

        List<References> result = referenceDAO.findAll();

        assertEquals(expectedReferences, result);
    }

    @Test
    public void saveTest() {
        References referenceToSave = new References();

        referenceDAO.save(referenceToSave);

        verify(mockReferenceRepository).save(referenceToSave);
    }

    @Test
    public void findByProduct_IdTest() {
        Long productId = 1L;
        List<References> expectedReferences = Arrays.asList(new References(), new References());
        when(mockReferenceRepository.findByProduct_Id(productId)).thenReturn(expectedReferences);

        List<References> result = referenceDAO.findByProduct_Id(productId);

        assertEquals(expectedReferences, result);
    }
}
