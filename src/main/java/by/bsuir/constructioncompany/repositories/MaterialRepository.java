package by.bsuir.constructioncompany.repositories;

import by.bsuir.constructioncompany.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByIsAvailableTrue();
}
