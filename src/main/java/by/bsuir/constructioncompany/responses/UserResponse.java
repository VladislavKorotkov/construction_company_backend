package by.bsuir.constructioncompany.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String phoneNumber;
    private Boolean isBlocked;
    private String role;
}
