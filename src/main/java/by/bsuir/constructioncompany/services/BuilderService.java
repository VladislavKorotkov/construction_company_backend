package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.models.User;

import java.util.List;

public interface BuilderService {
    void createBuilder(Builder builder);
    List<Builder> getBuilders();
    List<Builder> getBuildersBySpecialization();
    Builder getBuilder(long id);
    Builder getBuilder(User user);
}
