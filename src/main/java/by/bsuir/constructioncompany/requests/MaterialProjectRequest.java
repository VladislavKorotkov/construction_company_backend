package by.bsuir.constructioncompany.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialProjectRequest {
    @NotNull(message = "Id материала не может быть пустым")
    private Long materialId;

    @NotNull(message = "Количество не может быть пустым")
    private int quantity;

}
