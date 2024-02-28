package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> getApplicationByUser(User user);
}
