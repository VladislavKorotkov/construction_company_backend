package by.bsuir.constructioncompany.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequest {
    @NotBlank(message = "Email не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
