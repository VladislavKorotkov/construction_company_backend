package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.requests.ApplicationRequest;
import by.bsuir.constructioncompany.services.ApplicationService;
import by.bsuir.constructioncompany.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final AuthenticationService authenticationService;
    public ApplicationController(ApplicationService applicationService, AuthenticationService authenticationService) {
        this.applicationService = applicationService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FOREMAN')")
    public ResponseEntity<List<Application>> getApplications(){
        return ResponseEntity.ok(applicationService.getApplications());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Application>> getApplicationsByUser(Principal principal){
        return ResponseEntity.ok(applicationService.getApplicationsByUser(authenticationService.getUserByPrincipal(principal)));
    }

    @PostMapping
    public ResponseEntity<String> createApplication(@RequestBody ApplicationRequest applicationRequest, Principal principal){
        applicationService.createApplication(applicationRequest, authenticationService.getUserByPrincipal(principal));
        return ResponseEntity.ok("Заявка добавлена");
    }

    @PostMapping("/accept/{id}")
    @PreAuthorize("hasRole('FOREMAN')")
    public ResponseEntity<String> acceptApplication(@PathVariable("id") Long id, Principal principal){
        applicationService.acceptTheApplication(id, authenticationService.getUserByPrincipal(principal));
        return ResponseEntity.ok("Заявка принята");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@projectSecurity.hasUserAccessToApplication(#id, #principal)")
    public ResponseEntity<String> deleteApplication(@PathVariable("id") Long id, Principal principal){
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Заявка удалена");
    }
}
