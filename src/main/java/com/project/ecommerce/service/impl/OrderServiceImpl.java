package com.project.ecommerce.service.impl;


import com.project.ecommerce.entities.Cart;
import com.project.ecommerce.entities.Order;
import com.project.ecommerce.entities.OrderDetail;
import com.project.ecommerce.persistence.IOrderDAO;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.service.IOrderDetailService;
import com.project.ecommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
   @Autowired
   IOrderDAO orderDAO;
   @Autowired
   UserRepository userRepository;
   @Autowired
   ICartService cartService;
   @Autowired
   IOrderDetailService orderDetailService;


    @Override
    public List<Order> findByUser_Username(String username) {
        return orderDAO.findByUser_Username(username);
    }

    @Override
    public Optional<Order> findFirstByUserUsernameOrderByDateDesc(String username) {
        return orderDAO.findFirstByUserUsernameOrderByDateDesc(username);
    }

    @Override
    public Optional<Order> findLastActiveOrderByUsername(String username) {
        return orderDAO.findLastActiveOrderByUsername(username);
    }

    @Override
    public void disableOrderById(Long orderId) {
        orderDAO.disableOrderById(orderId);

    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }



    @Override
    public List<Order> findActiveOrdersByUsername(String username) {
        return orderDAO.findActiveOrdersByUsername(username);
    }

    @Override
    public void createOrder(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);

        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            List<Cart> shoppingCartList = cartService.findByUserEntityUsername(username);

            BigDecimal total = calculateTotal(shoppingCartList);

            Order order = createOrderEntity(total, user);

            Order savedOrder = orderDAO.save(order);

            saveOrderDetails(savedOrder, shoppingCartList);

            cartService.cleanCartByUserId(user.getId());
        }
    }

    public BigDecimal calculateTotal(List<Cart> shoppingCartList) {
        return shoppingCartList.stream()
                .map(shoppingCartItem -> {
                    BigDecimal price = shoppingCartItem.getProduct().getPrice();
                    BigDecimal amount = BigDecimal.valueOf(shoppingCartItem.getAmount());
                    return price.multiply(amount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order createOrderEntity(BigDecimal total, UserEntity user) {
        return new Order(total, new Date(), user, true);
    }

    public void saveOrderDetails(Order order, List<Cart> shoppingCartList) {
        for (Cart cart : shoppingCartList) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(cart.getProduct());
            detail.setAmount(cart.getAmount());
            orderDetailService.save(detail);
        }
    }

    /*@Override
    public void createOrder(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            List<Cart> shoppingCartList = cartService.findByUserEntityUsername(username);
            DecimalFormat decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
            decimalFormat.setRoundingMode(RoundingMode.DOWN);

            BigDecimal total = shoppingCartList.stream()
                    .map(shoppingCartItem -> {
                        BigDecimal price = shoppingCartItem.getProduct().getPrice();
                        BigDecimal amount = BigDecimal.valueOf(shoppingCartItem.getAmount());
                        return price.multiply(amount);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Order order = new Order(total, new Date(), user, true);
            Order orderSave = orderDAO.save(order);

            for(Cart cart: shoppingCartList){
                OrderDetail detail = new OrderDetail();
                detail.setOrder(orderSave);
                detail.setProduct(cart.getProduct());
                detail.setAmount(cart.getAmount());
                orderDetailService.save(detail);

            }

            cartService.cleanCartByUserId(user.getId());
        }

    }*/
}
