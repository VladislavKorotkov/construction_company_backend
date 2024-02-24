package by.bsuir.constructioncompany.models;

import by.bsuir.constructioncompany.models.enums.TaskStatus;
import by.bsuir.constructioncompany.models.enums.UnitWork;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "task")
@Data
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
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;
}
