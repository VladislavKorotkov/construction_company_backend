package by.bsuir.constructioncompany.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequest {
    @NotNull(message = "Id работы не может быть пустым")
    private long workProjectId;
    @NotNull(message = "Id строителя не может быть пустым")
    private long builderId;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
}
