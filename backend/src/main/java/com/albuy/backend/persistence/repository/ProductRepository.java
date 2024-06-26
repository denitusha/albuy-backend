package com.albuy.backend.persistence.repository;

import com.albuy.backend.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContaining(@Param("name") String name);


    List<Product> findAllByCategoryCategoryName(@Param("categoryName") String categoryName);

    List<Product> findAllBySellerId(@Param("sellerId") Long sellerId);

}
