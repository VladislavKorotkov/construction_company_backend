package by.bsuir.constructioncompany.models;

import by.bsuir.constructioncompany.models.enums.TaskStatus;
import by.bsuir.constructioncompany.models.enums.UnitWork;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "work_project_id", nullable = false)
    private WorkProject workProject;

    @ManyToOne
    @JoinColumn(name = "builder_id", nullable = false)
    private Builder builder;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @lombok.Builder.Default
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus = TaskStatus.PENDING;
}
