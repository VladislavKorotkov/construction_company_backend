package by.bsuir.constructioncompany.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TokenValidationResponse {
    private boolean isAccessTokenValid;
}
