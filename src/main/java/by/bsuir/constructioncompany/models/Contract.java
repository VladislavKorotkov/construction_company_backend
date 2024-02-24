package by.bsuir.constructioncompany.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "contract")
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @NotNull
    @Column(name = "file", nullable = false)
    @Lob
    private byte[] file;

}
