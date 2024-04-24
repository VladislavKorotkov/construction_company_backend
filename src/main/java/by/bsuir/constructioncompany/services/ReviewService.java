package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Review;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.ReviewRequest;
import by.bsuir.constructioncompany.responses.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> getReviews();
    void createReview(ReviewRequest reviewRequest, long projectId, User user);
}
