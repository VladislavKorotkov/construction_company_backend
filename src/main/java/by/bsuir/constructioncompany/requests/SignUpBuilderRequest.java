package by.bsuir.constructioncompany.requests;

import by.bsuir.constructioncompany.models.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpBuilderRequest {
    @NotBlank(message = "Email не может быть пустым")
    @Size(min=5, max = 30, message = "Размер email должен быть от 5 до 30")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min=5, max = 30, message = "Длина пароля должна быть от 5 до 30")
    private String password;

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotBlank(message = "Фамилия не может быть пустой")
    private String surname;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Pattern(regexp = "^\\+375-(?:17|25|29|33|44|55|99)-\\d{3}-\\d{2}-\\d{2}$", message = "Номер телефона должен быть вида +375-код-111-11-11")
    private String phoneNumber;

    @NotNull(message = "Id специальности не может быть пустым")
    private Long specialization_id;
}
