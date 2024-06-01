package com.albuy.backend.persistence.repository;


import com.albuy.backend.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



   // List<Order> findByCustomerEmail(@Param("email") String email);
}
