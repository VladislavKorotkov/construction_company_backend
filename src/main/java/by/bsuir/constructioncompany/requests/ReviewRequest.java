package by.bsuir.constructioncompany.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotBlank(message = "Отзыв не может быть пустым")
    private String message;
}
