package by.bsuir.constructioncompany.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse {
    private String message;
    private String userName;
}
