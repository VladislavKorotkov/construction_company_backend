package by.bsuir.constructioncompany.requests;

import by.bsuir.constructioncompany.models.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusChangeRequest {
    @NotNull(message = "Статус не может быть пустым")
    private TaskStatus taskStatus;
}
