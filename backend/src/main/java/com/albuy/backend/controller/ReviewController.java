package com.albuy.backend.controller;


import com.albuy.backend.persistence.dto.AddReviewDto;
import com.albuy.backend.persistence.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/seller-stats")
    public void addReview(@RequestBody AddReviewDto review) {
        log.info("Adding review");
        reviewService.addReview(review);
    }
}
