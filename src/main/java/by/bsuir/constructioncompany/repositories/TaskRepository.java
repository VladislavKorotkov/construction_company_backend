package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
