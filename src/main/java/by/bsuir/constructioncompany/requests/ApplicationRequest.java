package by.bsuir.constructioncompany.requests;

import by.bsuir.constructioncompany.models.Address;
import by.bsuir.constructioncompany.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {
    @NotBlank(message = "Имя заявки не может быть пустым")
    private String name;

    @NotNull(message = "Адрес не может быть пустым")
    private AddressRequest address;

    private String description;
}
