package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.Purchase;
import com.albuy.backend.persistence.dto.PurchaseResponse;
import com.albuy.backend.persistence.entity.Order;
import com.albuy.backend.persistence.entity.OrderItem;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.OrderItemRepository;
import com.albuy.backend.persistence.repository.OrderRepository;
import com.albuy.backend.persistence.repository.UserRepository;
import com.albuy.backend.persistence.service.CheckoutService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final JdbcTemplate jdbcTemplate;


    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order = purchase.getOrder();

        // Generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Associate order items with order
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> {
            order.add(item);
        });


        log.info("Order: " + order);

        // Retrieve existing customer information and set it to order
        User buyer = purchase.getBuyer();
        User existingCustomer = userRepository.findByEmail(buyer.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + buyer.getEmail()));
        order.setBuyer(existingCustomer);

        // Save the order, which will also save the order items due to cascading
        Order savedOrder = orderRepository.save(order);

        savedOrder.getOrderItems().forEach(item -> {
            setOrderId(savedOrder.getId(), item.getId());

        });

        return new PurchaseResponse(orderTrackingNumber);
    }


    private String generateOrderTrackingNumber() {


        return UUID.randomUUID().toString();
    }


    void setOrderId(Long orderId, Long orderItemID){
        String sql = "Update order_item set order_id = " + orderId +" where id = " + orderItemID;
        log.info(sql);
        jdbcTemplate.execute(sql);
    }
}
