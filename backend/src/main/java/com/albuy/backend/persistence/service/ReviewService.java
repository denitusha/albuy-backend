package com.albuy.backend.persistence.service;

import com.albuy.backend.persistence.dto.AddReviewDto;
import com.albuy.backend.persistence.dto.ReviewDto;
import com.albuy.backend.persistence.entity.Review;

public interface ReviewService {
    void addReview(AddReviewDto review);
}
