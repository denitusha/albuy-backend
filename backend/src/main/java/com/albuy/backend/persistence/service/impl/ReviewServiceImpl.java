package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.AddReviewDto;
import com.albuy.backend.persistence.entity.Review;
import com.albuy.backend.persistence.repository.ProductRepository;
import com.albuy.backend.persistence.repository.ReviewRepository;
import com.albuy.backend.persistence.repository.UserRepository;
import com.albuy.backend.persistence.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public void addReview(AddReviewDto review) {
        log.info("Adding review: {}", review);
        reviewRepository.save(Review.builder()
                .description(review.getDescription())
                .rating(review.getRating())
                .user(userRepository.findById(review.getUser()).orElse(null))
                .product(productRepository.findById(review.getProduct()).orElse(null))
                .build());
    }


}
