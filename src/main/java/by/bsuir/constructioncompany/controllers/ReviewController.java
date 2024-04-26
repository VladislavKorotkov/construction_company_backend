package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.requests.ReviewRequest;
import by.bsuir.constructioncompany.responses.ReviewResponse;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final AuthenticationService authenticationService;

    public ReviewController(ReviewService reviewService, AuthenticationService authenticationService) {
        this.reviewService = reviewService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getReviews());
    }

    @PreAuthorize("@projectSecurity.hasUserAccessToProject(#id, #principal)")
    @PostMapping("/project/{id}")
    public ResponseEntity<String> addReview(@PathVariable int id, @RequestBody ReviewRequest reviewRequest, Principal principal) {
        reviewService.createReview(reviewRequest, id, authenticationService.getUserByPrincipal(principal));
        return ResponseEntity.ok("Отзыв добавлен");
    }
}
