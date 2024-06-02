package com.albuy.backend.persistence.service.impl;

import com.albuy.backend.persistence.dto.AddReviewDto;
import com.albuy.backend.persistence.entity.Product;
import com.albuy.backend.persistence.entity.Review;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.ProductRepository;
import com.albuy.backend.persistence.repository.ReviewRepository;
import com.albuy.backend.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addReviewCreatesNewReviewForExistingUserAndProduct() {
        AddReviewDto reviewDto = AddReviewDto.builder()
                .user(1L)
                .description("Great product!")
                .rating(5)
                .product(1L)
                .build();


        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        reviewService.addReview(reviewDto);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }


}