package by.bsuir.constructioncompany.models;

import by.bsuir.constructioncompany.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email не может быть пустым")
    @Column(name = "username", nullable = false, unique = true)
    @Size(min=5, max = 30, message = "Размер email должен быть от 5 до 30")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Column(name = "password", nullable = false)
    @Size(min=5, max = 30, message = "Длина пароля должна быть от 5 до 30")
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
    private Role role = Role.USER;

    @NotBlank(message = "Имя не может быть пустым")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotBlank(message = "Номер телефона не может быть пустым")
    @Column(name = "phone_number", nullable = false)
    private String PhoneNumber;

    @Column(name = "is_blocked", nullable = false, columnDefinition="BOOLEAN DEFAULT false")
    private Boolean isBlocked = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Builder builder;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Application> applications;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Project> projectsUser;

    @OneToMany(mappedBy = "foreman")
    @JsonIgnore
    private List<Project> projectsForeman;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews;
}
