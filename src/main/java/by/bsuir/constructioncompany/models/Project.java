package by.bsuir.constructioncompany.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="date_of_creation", nullable = false)
    private LocalDateTime dateOfCreation;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name = "is_completed", nullable = false, columnDefinition="BOOLEAN DEFAULT false")
    @Builder.Default
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

    @Column(name = "contract_file", columnDefinition = "BLOB(50000)")
    @Lob
    @JsonIgnore
    private byte[] contractFile;

    @OneToOne(mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnore
    private Review review;
}
