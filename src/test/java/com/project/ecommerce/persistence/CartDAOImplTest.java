package com.project.ecommerce.persistence;

import com.project.ecommerce.entities.Cart;
import com.project.ecommerce.entities.Category;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.persistence.impl.CartDAOImpl;
import com.project.ecommerce.repository.CartRepository;
import com.project.ecommerce.security.entities.ERole;
import com.project.ecommerce.security.entities.RoleEntity;
import com.project.ecommerce.security.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartDAOImplTest {
    @Mock
    private CartRepository repository;
    @InjectMocks
    private CartDAOImpl cartDAOImpl;

    private Cart cart;
    private Product product;
    private UserEntity user;
    private Category category;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        user = UserEntity.builder()
                .email("molina@gmail.com")
                .name("Ramiro")
                .enabled(true)
                .username("ronaldinho")
                .password("12345")
                .roles(Set.of(RoleEntity.builder()
                        .name(ERole.valueOf(ERole.CUSTOMER.name()))
                        .build()))
                .build();
        category = Category.builder()
                .enabled(true)
                .name("nameTest")
                .build();

        product = Product.builder()
                .price(BigDecimal.valueOf(234.4))
                .name("nameTest")
                .brand("Cualquiera")
                .category(category)
                .stock(23)
                .build();

        cart = Cart.builder()
                .user(user)
                .amount(3)
                .product(product)
                .build();
    }

    @Test
    public void findByUserEntityUsernameTest( ){
        when(repository.findByUser_Username(user.getUsername())).thenReturn(Arrays.asList(cart));
        assertNotNull(cartDAOImpl.findByUserEntityUsername(user.getUsername()));
    }

    @Test
    public void findByUserEntityIdTest() {
        when(repository.findByUser_Id(1L)).thenReturn(Arrays.asList(cart));

        List<Cart> result = cartDAOImpl.findByUserEntityId(1L);

        assertEquals(1, result.size());
    }

    @Test
    public void cleanCartByUserIdTest() {
        // No necesitas configurar un retorno, ya que es una operación void
        cartDAOImpl.cleanCartByUserId(1L);

        // Verifica que se haya llamado a deleteByUser_Id con el argumento correcto
        verify(repository).deleteByUser_Id(1L);
    }

    @Test
    public void countByUserEntityIdTest() {
        when(repository.countByUser_Id(1L)).thenReturn(5L);

        Long result = cartDAOImpl.countByUserEntityId(1L);

        assertEquals(5L, result.longValue());
    }

    @Test
    public void addProductTest() {
        cartDAOImpl.addProduct(cart);

        // Verifica que se haya llamado a save con el argumento correcto
        verify(repository).save(cart);
    }

    @Test
    public void deleteCartByIdTest() {
        cartDAOImpl.deleteCartById(1L);

        // Verifica que se haya llamado a deleteById con el argumento correcto
        verify(repository).deleteById(1L);
    }

}
