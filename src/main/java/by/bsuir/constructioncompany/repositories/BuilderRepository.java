package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuilderRepository extends JpaRepository<Builder, Long> {
}
