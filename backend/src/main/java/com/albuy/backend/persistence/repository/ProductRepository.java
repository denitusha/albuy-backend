package com.albuy.backend.persistence.repository;

import com.albuy.backend.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource()
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);


}
