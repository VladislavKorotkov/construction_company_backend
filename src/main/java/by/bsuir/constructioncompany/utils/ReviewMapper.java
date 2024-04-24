package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.Review;
import by.bsuir.constructioncompany.responses.ReviewResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {
    public static List<ReviewResponse> mapToResponseList(List<Review> reviews) {
        return reviews.stream().map(ReviewMapper::mapToResponse).collect(Collectors.toList());
    }
    public static ReviewResponse mapToResponse(Review review){
        return ReviewResponse.builder()
                .message(review.getText())
                .userName(review.getUser().getSurname()+ " " + review.getUser().getName())
                .build();
    }
}
