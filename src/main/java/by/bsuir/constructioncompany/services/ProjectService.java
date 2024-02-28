package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public void createProject(Application application, User foreman){
        Project project = Project.builder()
                .name(application.getName())
                .user(application.getUser())
                .address(application.getAddress())
                .dateOfCreation(LocalDateTime.now())
                .foreman(foreman)
                .build();
        projectRepository.save(project);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public List<Project> getAllProjectsByForeman(User foreman){
        return projectRepository.findByForeman(foreman);
    }

    public List<Project> getAllProjectsByUser(User user){
        return projectRepository.findByUser(user);
    }

}
