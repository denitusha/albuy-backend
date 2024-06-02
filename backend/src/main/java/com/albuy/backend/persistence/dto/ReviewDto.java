package com.albuy.backend.persistence.dto;

import com.albuy.backend.persistence.entity.Product;
import com.albuy.backend.persistence.entity.Review;
import com.albuy.backend.persistence.entity.User;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {


    private Long id;
    private String description;

    private float rating;


    private String user;

    public static ReviewDto fromReview(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .description(review.getDescription())
                .rating(review.getRating())
                .user(review.getUser().getUsername())
                .build();
    }

}
