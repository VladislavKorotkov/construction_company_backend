package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.LackOfRightsException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.ProjectRepository;
import by.bsuir.constructioncompany.requests.MaterialProjectRequest;
import by.bsuir.constructioncompany.requests.ProjectEstimateRequest;
import by.bsuir.constructioncompany.requests.WorkProjectRequest;
import by.bsuir.constructioncompany.responses.EstimateResponse;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;
import by.bsuir.constructioncompany.utils.CalculateTotalCost;
import by.bsuir.constructioncompany.utils.EstimateToXlsx;
import by.bsuir.constructioncompany.utils.GenerateContract;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MaterialProjectService materialProjectService;
    private final WorkProjectService workProjectService;

    public ProjectService(ProjectRepository projectRepository, MaterialProjectService materialProjectService, WorkProjectService workProjectService) {
        this.projectRepository = projectRepository;
        this.materialProjectService = materialProjectService;
        this.workProjectService = workProjectService;
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

    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Проект не найден"));
    }

    @Transactional
    public void createEstimate(Long id, ProjectEstimateRequest projectEstimateRequest){
        Project project = getProjectById(id);
        workProjectService.createWorkProjects(projectEstimateRequest.getWorks(), project);
        materialProjectService.createMaterialProjects(projectEstimateRequest.getMaterials(), project);
    }

    @Transactional
    public void createMaterialEstimate(Long id, MaterialProjectRequest materialProjectRequest){
        Project project = getProjectById(id);
        materialProjectService.createMaterialProject(materialProjectRequest, project);
    }

    @Transactional
    public void createWorkEstimate(Long id, WorkProjectRequest workProjectRequest){
        Project project = getProjectById(id);
        workProjectService.createWorkProject(workProjectRequest, project);
    }

    public EstimateResponse getEstimate(Long id){
        Project project = getProjectById(id);
        List<MaterialProjectResponse> materialProjectResponses = materialProjectService.getMaterialProjectResponses(project);
        List<WorkProjectResponse> workProjectResponses = workProjectService.getWorkProjectResponses(project);
        return EstimateResponse.builder()
                .projectId(project.getId())
                .works(workProjectResponses)
                .materials(materialProjectResponses)
                .totalCost(CalculateTotalCost.calculate(workProjectResponses, materialProjectResponses))
                .build();
    }

    public int getTotalCost(Long id){
        Project project = getProjectById(id);
        return CalculateTotalCost.calculate(workProjectService.getWorkProjectResponses(project), materialProjectService.getMaterialProjectResponses(project));
    }

    @Transactional
    public void deleteWorkEstimate(Long projectId, Long workId){
        Project project = getProjectById(projectId);
        workProjectService.deleteWorkEstimate(project, workId);
    }

    @Transactional
    public void deleteMaterialEstimate(Long projectId, Long materialId){
        Project project = getProjectById(projectId);
        materialProjectService.deleteMaterialProject(project, materialId);
    }

    public byte[] getEstimateXlsx(Long projectId){
        EstimateResponse estimateResponse = getEstimate(projectId);
        return EstimateToXlsx.convertEstimateResponseToXlsx(estimateResponse);
    }

    @Transactional
    public void generateContract(Long projectId){

        Project project = getProjectById(projectId);
        GenerateContract.generate(project, getTotalCost(projectId));
    }
}
