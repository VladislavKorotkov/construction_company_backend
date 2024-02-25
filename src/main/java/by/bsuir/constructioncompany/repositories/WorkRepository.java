package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
}
