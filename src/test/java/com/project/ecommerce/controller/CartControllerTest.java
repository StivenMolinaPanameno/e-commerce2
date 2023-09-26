package com.project.ecommerce.controller;

import com.project.ecommerce.controller.DTO.CartDTO;
import com.project.ecommerce.entities.Cart;
import com.project.ecommerce.entities.Product;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.service.ICartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

   @Mock
    private ICartService cartService;

    @InjectMocks
    private CartController cartController;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void testGetListByClient() {
        // Simula el nombre de usuario autenticado
        Cart cart = new Cart();
        cart.setAmount(12);
        cart.setUser(new UserEntity());
        cart.setProduct(new Product());
        String username = "testUsername";

        when(cartService.findByUserEntityUsername(username)).thenReturn(Arrays.asList(cart));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, "password"));

        ResponseEntity<?> responseEntity = cartController.getListByClient();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void testCountByUser() {
        // Simula el recuento de productos en el carrito
        when(cartService.countByUserEntityId(Mockito.anyLong())).thenReturn(10L);

        ResponseEntity<Long> responseEntity = cartController.countByUser(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testAddProduct() throws UserNotFoundException {
        CartDTO cartDTO = new CartDTO();

        ResponseEntity<?> responseEntity = cartController.addProdudct(cartDTO, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    public void testRemoveProduct() throws Exception {
        Long itemId = 1L; // Puedes usar cualquier ID válido aquí

        // Configura el mock para que no lance excepciones
        Mockito.doNothing().when(cartService).deleteCartById(itemId);

        ResponseEntity<?> responseEntity = cartController.removeProduct(itemId);

        assertEquals(ResponseEntity.ok("Products Deleted"), responseEntity);
    }
}


    /*@GetMapping()
    public ResponseEntity<?> getListByClient(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(cartService.findByUserEntityUsername(username), HttpStatus.OK);
    }
    @GetMapping("/count/{id}")
    public ResponseEntity<Long> countByUser(@PathVariable Long id){
        return new ResponseEntity<>(cartService.countByUserEntityId(id), HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProdudct(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult) throws UserNotFoundException {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid Fields");
        }
        cartService.addProduct(cartDTO);
        return ResponseEntity.ok(cartDTO);

    }
    @DeleteMapping("/clean/{item_id}")
    public ResponseEntity<?> removeProduct(@PathVariable("item_id")Long id) throws Exception {
        cartService.deleteCartById(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }*/
