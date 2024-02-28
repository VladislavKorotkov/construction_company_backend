package by.bsuir.constructioncompany.models;

import by.bsuir.constructioncompany.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@lombok.Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @Size(min=5, max = 30, message = "Размер email должен быть от 5 до 30")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min=5, max = 30, message = "Длина пароля должна быть от 5 до 30")
    @JsonIgnore
    private String password;

    @lombok.Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ROLE_USER'")
    private Role role = Role.ROLE_USER;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp = "^\\+375-(?:17|25|29|33|44|55|99)-\\d{3}-\\d{2}-\\d{2}$", message = "Номер телефона должен быть вида +375-код-111-11-11")
    private String phoneNumber;

    @lombok.Builder.Default
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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
