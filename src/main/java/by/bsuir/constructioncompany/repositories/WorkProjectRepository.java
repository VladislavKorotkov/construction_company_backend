package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.WorkProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkProjectRepository extends JpaRepository<WorkProject, Long> {
    List<WorkProject> findByProject(Project project);
    Optional<WorkProject> findByProjectAndId(Project project, Long id);
    List<WorkProject> findByProjectAndTaskIsNull(Project project);
}
