package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTaskByBuilder(Builder builder);
}
