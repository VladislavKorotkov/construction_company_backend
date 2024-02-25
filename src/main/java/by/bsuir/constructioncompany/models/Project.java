package by.bsuir.constructioncompany.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название заказа не может быть пустым")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="date_of_creation", nullable = false)
    @NotNull(message = "Дата создания заказа не может быть пустой")
    private LocalDateTime dateOfCreation;

    @Column(name="start_date")
    @NotNull(message = "Дата начала работ не может быть пустой")
    private LocalDateTime startDate;

    @Column(name="end_date")
    @NotNull(message = "Дата окончания работ не может быть пустой")
    private LocalDateTime endDate;

    @Column(name = "is_completed", nullable = false, columnDefinition="BOOLEAN DEFAULT false")
    private Boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="foreman_id", nullable = false)
    private User foreman;

    @ManyToOne
    @JoinColumn(name="address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<MaterialProject> materialProjects;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<WorkProject> workProjects;

    @Column(name = "contract_file")
    @Lob
    @JsonIgnore
    private byte[] contractFile;

    @OneToOne(mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnore
    private Review review;
}
