package com.albuy.backend.persistence.repository;

import com.albuy.backend.persistence.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository  extends JpaRepository<OrderItem, Long> {
}
