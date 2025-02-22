package com.everylog.Everylog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.everylog.Everylog.dto.Review;
import com.everylog.Everylog.model.ReviewModel;
import com.everylog.Everylog.service.ReviewsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {
    @Autowired
    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewReview(@RequestBody Review review) {
        reviewsService.createReview(review);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/reviews")
    public List<ReviewModel> getReviewsByUsername(@RequestParam String username) {
        return reviewsService.getReviewsByUsername(username);
    }

}
