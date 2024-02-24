package by.bsuir.constructioncompany.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название города не может быть пустым")
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank(message = "Название улицы не может быть пустым")
    @Column(name = "street", nullable = false)
    private String street;

    @NotBlank(message = "Номер дома не может быть пустым")
    @Column(name = "number_house", nullable = false)
    private String numberHouse;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Application> applications;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Project> projects;
}
