package by.bsuir.constructioncompany.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number_house", nullable = false)
    private String numberHouse;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Application> applications;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Project> projects;

    public Address(String city, String street, String numberHouse) {
        this.city = city;
        this.street = street;
        this.numberHouse = numberHouse;
    }

}
