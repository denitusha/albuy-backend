package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.SellerStats;
import com.albuy.backend.persistence.entity.OrderItem;
import com.albuy.backend.persistence.entity.Product;
import com.albuy.backend.persistence.repository.OrderItemRepository;
import com.albuy.backend.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SellerServiceImplTest {

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getSellerStatsReturnsCorrectStatsForExistingSeller() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductId(1L);
        orderItem1.setQuantity(2);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProductId(2L);
        orderItem2.setQuantity(3);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setSellerId(1L);
        product1.setPrice(BigDecimal.valueOf(100));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setSellerId(1L);
        product2.setPrice(BigDecimal.valueOf(200));

        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(orderItem1, orderItem2));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));

        SellerStats stats = sellerService.getSellerStats(1L);

        assertEquals(5, stats.getTotalUnitsSold());
        assertEquals(800, stats.getTotalRevenue().intValue());
    }

    @Test
    public void getSellerStatsReturnsZeroStatsForNonExistingSeller() {
        when(orderItemRepository.findAll()).thenReturn(Arrays.asList());

        SellerStats stats = sellerService.getSellerStats(1L);

        assertEquals(0, stats.getTotalUnitsSold());
        assertEquals(0, stats.getTotalRevenue().intValue());
    }
}