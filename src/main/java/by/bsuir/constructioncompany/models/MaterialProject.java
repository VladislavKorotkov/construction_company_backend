package by.bsuir.constructioncompany.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_project")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private int costOld;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
