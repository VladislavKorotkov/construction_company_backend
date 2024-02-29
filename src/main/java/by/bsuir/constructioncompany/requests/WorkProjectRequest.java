package by.bsuir.constructioncompany.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkProjectRequest {
    @NotNull(message = "Id услуги не может быть пустым")
    private Long workId;

    @NotNull(message = "Количество не может быть пустым")
    private int quantity;

}
