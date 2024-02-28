package by.bsuir.constructioncompany.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SpecializationRequest {
    @NotBlank(message = "Название специальности не может быть пустым")
    private String name;
}
