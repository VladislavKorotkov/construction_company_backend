package by.bsuir.constructioncompany.security;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.ProjectService;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class ProjectSecurity {
    private final ProjectService projectService;
    private final AuthenticationService authenticationService;

    public ProjectSecurity(ProjectService projectService, AuthenticationService authenticationService) {
        this.projectService = projectService;
        this.authenticationService = authenticationService;
    }

    public boolean hasForemanAccess(Long projectId, Principal principal) {
        User foreman = authenticationService.getUserByPrincipal(principal);
        return isUserForemanOfProject(foreman.getId(), projectId);
    }
    public boolean hasForemanOrUserAccess(Long projectId, Principal principal){
        User user = authenticationService.getUserByPrincipal(principal);
        return isUserHasAccessOfProject(user.getId(), projectId);
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
