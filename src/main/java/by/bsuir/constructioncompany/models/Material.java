package by.bsuir.constructioncompany.models;

import by.bsuir.constructioncompany.models.enums.UnitMaterial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "material")
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "is_available", nullable = false, columnDefinition="BOOLEAN DEFAULT true")
    private Boolean isAvailable = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private UnitMaterial unit;

    @OneToMany(mappedBy = "material")
    @JsonIgnore
    private List<MaterialProject> materialProjects;

}
