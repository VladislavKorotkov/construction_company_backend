package by.bsuir.constructioncompany.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_project")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private int costOld;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @OneToOne(mappedBy = "workProject", fetch = FetchType.LAZY)
    @JsonIgnore
    private Task task;
}
