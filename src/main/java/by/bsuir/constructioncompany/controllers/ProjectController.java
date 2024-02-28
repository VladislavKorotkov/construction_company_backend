package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final AuthenticationService authenticationService;

    public ProjectController(ProjectService projectService, AuthenticationService authenticationService) {
        this.projectService = projectService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/foreman")
    public ResponseEntity<List<Project>> getProjectsByForeman(Principal principal){
        return ResponseEntity.ok(projectService.getAllProjectsByForeman(authenticationService.getUserByPrincipal(principal)));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(){
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Project>> getProjectsByUser(Principal principal){
        return ResponseEntity.ok(projectService.getAllProjectsByUser(authenticationService.getUserByPrincipal(principal)));
    }
}
