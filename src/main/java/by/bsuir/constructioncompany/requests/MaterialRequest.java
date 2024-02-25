package by.bsuir.constructioncompany.requests;

import by.bsuir.constructioncompany.models.enums.UnitMaterial;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialRequest {
    @NotBlank(message = "Название материала не может быть пустым")
    private String name;

    @NotNull(message = "Цена не может быть пустой")
    private int cost;

    private Boolean isAvailable = true;

    @NotNull(message = "Единицы измерения не могут пустыми")
    private UnitMaterial unit;
}
