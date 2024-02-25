package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.WorkProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkProjectRepository extends JpaRepository<WorkProject, Long> {
}
