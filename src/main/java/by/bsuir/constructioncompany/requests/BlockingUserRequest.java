package by.bsuir.constructioncompany.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlockingUserRequest {
    private Boolean isBlocked;
}
