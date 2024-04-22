package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.BuilderRepository;
import by.bsuir.constructioncompany.services.BuilderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuilderServiceImpl implements BuilderService {
    private final BuilderRepository builderRepository;

    public BuilderServiceImpl(BuilderRepository builderRepository) {
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

    public Builder getBuilder(long id){
        return builderRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Строитель не найден"));
    }

    public Builder getBuilder(User user){
        return builderRepository.findBuilderByUser(user).orElseThrow(()-> new ObjectNotFoundException("Строитель не найден"));
    }
}
