package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.requests.ContractRequest;
import by.bsuir.constructioncompany.requests.MaterialProjectRequest;
import by.bsuir.constructioncompany.requests.ProjectEstimateRequest;
import by.bsuir.constructioncompany.requests.WorkProjectRequest;
import by.bsuir.constructioncompany.responses.EstimateResponse;
import by.bsuir.constructioncompany.responses.ProjectResponse;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @PreAuthorize("@projectSecurity.hasUserOrAnyRoleAccessToProject(#id, #principal)")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("id") Long id, Principal principal){
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    @PostMapping("/{id}/estimate")
    public ResponseEntity<String> createEstimate(@RequestBody ProjectEstimateRequest projectEstimateRequest, @PathVariable("id") Long id, Principal principal){
        projectService.createEstimate(id, projectEstimateRequest);
        return ResponseEntity.ok("Смета составлена");
    }
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    @PostMapping("/{id}/estimate/materials")
    public ResponseEntity<String> createMaterialEstimate(@RequestBody MaterialProjectRequest materialProjectRequest, @PathVariable("id") Long id, Principal principal){
        projectService.createMaterialEstimate(id, materialProjectRequest);
        return ResponseEntity.ok("Материал добавлен в смету");
    }
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    @PostMapping("/{id}/estimate/works")
    public ResponseEntity<String> createWorksEstimate(@RequestBody WorkProjectRequest workProjectRequest, @PathVariable("id") Long id, Principal principal){
        projectService.createWorkEstimate(id, workProjectRequest);
        return ResponseEntity.ok("Услуга добавлена в смету");
    }

    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    @DeleteMapping("/{id}/estimate/works/{workId}")
    public ResponseEntity<String> DeleteWorkEstimate(@PathVariable("id") Long id, @PathVariable("workId") Long workId, Principal principal){
        projectService.deleteWorkEstimate(id, workId);
        return ResponseEntity.ok("Услуга удалена");
    }
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    @DeleteMapping("/{id}/estimate/materials/{materialId}")
    public ResponseEntity<String> DeleteMaterialEstimate(@PathVariable("id") Long id, @PathVariable("materialId") Long materialId, Principal principal){
        projectService.deleteMaterialEstimate(id, materialId);
        return ResponseEntity.ok("Материал удален");
    }
    @GetMapping("/{id}/estimate")
    @PreAuthorize("@projectSecurity.hasForemanOrUserAccess(#id, #principal)")
    public ResponseEntity<EstimateResponse> getEstimate(@PathVariable("id") Long id, Principal principal){
        return ResponseEntity.ok(projectService.getEstimate(id));
    }
    @GetMapping("/{id}/estimate/xlsx")
    @PreAuthorize("@projectSecurity.hasForemanOrUserAccess(#id, #principal)")
    public ResponseEntity<byte[]> getEstimateXls(@PathVariable("id") Long id, Principal principal){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "estimate.xlsx");
        byte[] excelBytes = projectService.getEstimateXlsx(id);
        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    @PostMapping("/{id}/contract")
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    public ResponseEntity<String> generateContract(@PathVariable("id") Long id,@RequestBody @Valid ContractRequest contractRequest, Principal principal){
        projectService.generateContract(id, contractRequest);
        return ResponseEntity.ok("Договор составлен");
    }

    @GetMapping("/{id}/contract")
    @PreAuthorize("@projectSecurity.hasForemanOrUserAccess(#id, #principal)")
    public ResponseEntity<byte[]> generateContract(@PathVariable("id") Long id, Principal principal){
        byte[] contractFile = projectService.getContract(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "contract.docx");
        return ResponseEntity.ok()
                .headers(headers)
                .body(contractFile);
    }
    @GetMapping("/{id}/works")
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    public ResponseEntity<List<WorkProjectResponse>> getFreeWorksForProject(@PathVariable("id") Long id, Principal principal){
        return ResponseEntity.ok(projectService.getFreeWorkProjects(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    public ResponseEntity<String> updateStatusProject(@PathVariable("id") long id, Principal principal){
        projectService.updateStatusProject(id);
        return ResponseEntity.ok("Статус изменен");
    }
}
