package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.MaterialProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialProjectRepository extends JpaRepository<MaterialProject, Long> {
}
