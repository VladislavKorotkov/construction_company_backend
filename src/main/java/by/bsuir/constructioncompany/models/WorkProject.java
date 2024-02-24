package by.bsuir.constructioncompany.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "work_project")
@Data
public class WorkProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

    @Column(name = "cost_old", nullable = false)
    @NotNull(message = "Цена не может быть пустой")
    private int cost_old;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Объем работ не может быть пустым")
    private int quantity;

    @OneToOne(mappedBy = "workProject", fetch = FetchType.LAZY)
    private Task task;
}
