package by.bsuir.constructioncompany.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "builder")
@Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonIgnore
    private List<Task> tasks;
}
