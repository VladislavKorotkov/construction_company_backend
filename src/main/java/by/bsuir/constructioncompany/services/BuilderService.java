package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.repositories.BuilderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuilderService {
    private final BuilderRepository builderRepository;

    public BuilderService(BuilderRepository builderRepository) {
        this.builderRepository = builderRepository;
    }

    @Transactional
    public void createBuilder(Builder builder){
        builderRepository.save(builder);
    }

    public List<Builder> getBuilders(){
        return builderRepository.findAll();
    }

    public List<Builder> getBuildersBySpecialization(){
        return null;
    }
}
