package com.albuy.backend.persistence.repository;

import com.albuy.backend.persistence.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
