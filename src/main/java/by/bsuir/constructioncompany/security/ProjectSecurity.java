package by.bsuir.constructioncompany.security;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.Task;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.ProjectService;
import by.bsuir.constructioncompany.services.TaskService;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class ProjectSecurity {
    private final ProjectService projectService;
    private final AuthenticationService authenticationService;
    private final TaskService taskService;

    public ProjectSecurity(ProjectService projectService, AuthenticationService authenticationService, TaskService taskService) {
        this.projectService = projectService;
        this.authenticationService = authenticationService;
        this.taskService = taskService;
    }

    public boolean hasForemanAccess(Long projectId, Principal principal) {
        User foreman = authenticationService.getUserByPrincipal(principal);
        return isUserForemanOfProject(foreman.getId(), projectId);
    }
    public boolean hasForemanOrUserAccess(Long projectId, Principal principal){
        User user = authenticationService.getUserByPrincipal(principal);
        return isUserHasAccessOfProject(user.getId(), projectId);
    }
    public boolean hasBuilderAccess(Long taskId, Principal principal) {
        User builder = authenticationService.getUserByPrincipal(principal);
        return isBuilderOfTask(builder.getId(), taskId);
    }

    private boolean isBuilderOfTask(Long userId, Long id) {
        Task task = taskService.getTask(id);
        return task.getBuilder().getUser().getId().equals(userId);
    }


    private boolean isUserHasAccessOfProject(Long userId, Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return project.getForeman().getId().equals(userId) || project.getUser().getId().equals(userId);
    }



    private boolean isUserForemanOfProject(Long userId, Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return project.getForeman().getId().equals(userId);
    }

}
