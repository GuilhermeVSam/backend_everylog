package com.everylog.Everylog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everylog.Everylog.dto.Review;
import com.everylog.Everylog.model.ReviewModel;
import com.everylog.Everylog.repository.ReviewRepository;

@Service
public class ReviewsService {
    @Autowired
    private final ReviewRepository reviewRepository;

    public ReviewsService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void createReview(Review review) {
        List<ReviewModel> reviewsByUser = reviewRepository.findByUsername(review.getUsername());

        for (ReviewModel reviewModel : reviewsByUser) {
            if (reviewModel.getContentId().equals(review.getContentId())) {
                throw new RuntimeException("Content already reviewed");
            }
        }

        ReviewModel newReview = new ReviewModel();
        newReview.setContentType(review.getContentType());
        newReview.setContentId(review.getContentId());
        newReview.setRating(review.getRating());
        newReview.setReview(review.getReview());
        newReview.setUsername(review.getUsername());

        reviewRepository.save(newReview);
    }

    public Optional<ReviewModel> getReviewById(int id) {
        return reviewRepository.findById(id);
    }

    public List<ReviewModel> getReviewsByUsername(String username) {
        return reviewRepository.findByUsername(username);
    }

}
