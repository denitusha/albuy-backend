package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.SellerStats;
import com.albuy.backend.persistence.entity.Order;
import com.albuy.backend.persistence.entity.OrderItem;
import com.albuy.backend.persistence.entity.Product;
import com.albuy.backend.persistence.repository.OrderItemRepository;
import com.albuy.backend.persistence.repository.OrderRepository;
import com.albuy.backend.persistence.repository.ProductRepository;
import com.albuy.backend.persistence.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public SellerStats getSellerStats(Long sellerId) {
        AtomicInteger countOfUnit = new AtomicInteger(0);
        AtomicInteger revenue = new AtomicInteger(0);
        List<OrderItem> orderItems = orderItemRepository.findAll();

        orderItems.forEach(orderItem -> {
            Product product = productRepository.findById(orderItem.getProductId()).orElse(null);
            if (product != null && product.getSellerId().equals(sellerId)) {
                countOfUnit.addAndGet(orderItem.getQuantity());
                revenue.addAndGet(orderItem.getQuantity() * product.getPrice().intValue());
            }
        });

        return SellerStats.builder()
                .totalUnitsSold(countOfUnit.get())
                .totalRevenue(new BigDecimal(revenue.get()))
                .build();
    }






}
