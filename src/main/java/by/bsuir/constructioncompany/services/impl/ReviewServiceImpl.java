package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.repositories.ReviewRepository;
import by.bsuir.constructioncompany.services.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

   // public boolean createReview()
}
