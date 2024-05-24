package com.albuy.backend.persistence.repository;


import com.albuy.backend.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {




   // List<Order> findByCustomerEmail(@Param("email") String email);
}
