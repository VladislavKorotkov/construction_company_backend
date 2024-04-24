package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.IncorrectDataException;
import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.Review;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.ReviewRepository;
import by.bsuir.constructioncompany.requests.ReviewRequest;
import by.bsuir.constructioncompany.responses.ReviewResponse;
import by.bsuir.constructioncompany.services.ProjectService;
import by.bsuir.constructioncompany.services.ReviewService;
import by.bsuir.constructioncompany.utils.ReviewMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProjectService projectService;


    public ReviewServiceImpl(ReviewRepository reviewRepository, ProjectService projectService) {
        this.reviewRepository = reviewRepository;
        this.projectService = projectService;
    }


    @Override
    @Transactional
    public List<ReviewResponse> getReviews() {
        Pageable pageable = (Pageable) PageRequest.of(0,5);
        List<Review> reviews = reviewRepository.findAll(pageable);
        return ReviewMapper.mapToResponseList(reviews);
    }

    @Override
    @Transactional
    public void createReview(ReviewRequest reviewRequest, long projectId, User user) {
        Project project = projectService.getProjectById(projectId);
        if(project.getIsCompleted()&&project.getReview()!=null){
            Review review = Review.builder()
                    .text(reviewRequest.getMessage())
                    .date(LocalDateTime.now())
                    .user(user)
                    .project(project)
                    .build();
            reviewRepository.save(review);
        }
        else throw new IncorrectDataException("Отзыв уже оставлен");

    }

}
