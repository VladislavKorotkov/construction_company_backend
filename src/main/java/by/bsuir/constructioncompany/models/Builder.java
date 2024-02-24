package by.bsuir.constructioncompany.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "builder")
@Data
public class Builder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="specialization_id", nullable = false)
    private Specialization specialization;

    @OneToMany(mappedBy = "builder")
    private List<Task> tasks;
}
