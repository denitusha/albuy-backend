package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.Purchase;
import com.albuy.backend.persistence.dto.PurchaseResponse;
import com.albuy.backend.persistence.entity.Order;
import com.albuy.backend.persistence.entity.OrderItem;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.OrderRepository;
import com.albuy.backend.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CheckoutServiceImplTest {
    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void placeOrderCreatesNewOrderForExistingUser() {
        User user = new User();
        user.setEmail("test@test.com");

        Order order = new Order();
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(new OrderItem());
        Purchase purchase = Purchase.builder()
                .order(order)
                .orderItems(orderItems)
                .Buyer(user)
                .build();
        purchase.setOrder(order);
        purchase.setOrderItems(orderItems);
        purchase.setBuyer(user);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        PurchaseResponse response = checkoutService.placeOrder(purchase);

        assertNotNull(response);
        verify(orderRepository, times(1)).save(any(Order.class));
    }


}
