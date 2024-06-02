package com.albuy.backend.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image_url")
    private String imageUrl;
    @Column(name="unit_price")
    private BigDecimal unitPrice;
    @Column(name="quantity")
    private int quantity;
    @Column(name="product_id")
    private Long productId;



    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;



}
