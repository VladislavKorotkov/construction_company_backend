package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByForeman(User foreman);
    List<Project> findByUser(User user);
}
