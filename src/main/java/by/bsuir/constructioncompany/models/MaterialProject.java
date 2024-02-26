package by.bsuir.constructioncompany.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "material_project")
@Data
public class MaterialProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "cost_old", nullable = false)
    private int cost_old;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
