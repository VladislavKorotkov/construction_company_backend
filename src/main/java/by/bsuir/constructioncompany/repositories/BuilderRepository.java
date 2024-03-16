package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuilderRepository extends JpaRepository<Builder, Long> {
    Optional<Builder> findBuilderByUser(User user);
}
