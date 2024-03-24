package by.bsuir.constructioncompany.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class    AddressRequest {
    @NotBlank(message = "Название города не может быть пустым")
    private String city;

    @NotBlank(message = "Название улицы не может быть пустым")
    private String street;

    @NotBlank(message = "Номер дома не может быть пустым")
    private String numberHouse;

}
