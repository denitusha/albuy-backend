package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.Purchase;
import com.albuy.backend.persistence.dto.PurchaseResponse;
import com.albuy.backend.persistence.entity.Order;
import com.albuy.backend.persistence.entity.OrderItem;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.OrderRepository;
import com.albuy.backend.persistence.repository.UserRepository;
import com.albuy.backend.persistence.service.CheckoutService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;


    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));


        User buyer = purchase.getBuyer();
        User existingCustomer = userRepository.findByEmail(buyer.getEmail()).get();


        order.setBuyer(existingCustomer);
        orderRepository.save(order);


        return new PurchaseResponse(orderTrackingNumber);

    }

    private String generateOrderTrackingNumber() {


        return UUID.randomUUID().toString();
    }

}
