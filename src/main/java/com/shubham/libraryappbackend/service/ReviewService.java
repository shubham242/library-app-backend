package com.shubham.libraryappbackend.service;

import com.shubham.libraryappbackend.dao.BookRepository;
import com.shubham.libraryappbackend.dao.ReviewRepository;
import com.shubham.libraryappbackend.entity.Review;
import com.shubham.libraryappbackend.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    @Autowired
    public ReviewService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    
    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
        if (validateReview != null) {
            throw new Exception("Review already exists!");
        }
        
        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(Object::toString).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }
    
    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validateReview != null;
    }
}
