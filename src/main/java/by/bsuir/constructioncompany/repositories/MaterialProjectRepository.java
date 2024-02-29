package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.MaterialProject;
import by.bsuir.constructioncompany.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialProjectRepository extends JpaRepository<MaterialProject, Long> {
    List<MaterialProject> getMaterialProjectByProject(Project project);
    Optional<MaterialProject> findByProjectAndId(Project project, Long id);
}
