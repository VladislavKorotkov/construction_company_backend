package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.*;
import by.bsuir.constructioncompany.models.*;
import by.bsuir.constructioncompany.models.enums.TaskStatus;
import by.bsuir.constructioncompany.repositories.ProjectRepository;
import by.bsuir.constructioncompany.requests.ContractRequest;
import by.bsuir.constructioncompany.requests.MaterialProjectRequest;
import by.bsuir.constructioncompany.requests.ProjectEstimateRequest;
import by.bsuir.constructioncompany.requests.WorkProjectRequest;
import by.bsuir.constructioncompany.responses.EstimateResponse;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;
import by.bsuir.constructioncompany.responses.ProjectResponse;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;
import by.bsuir.constructioncompany.utils.CalculateTotalCost;
import by.bsuir.constructioncompany.utils.EstimateToXlsx;
import by.bsuir.constructioncompany.utils.GenerateContract;
import by.bsuir.constructioncompany.utils.ProjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Transactional
    public ProjectResponse getProject(Long id){
        Project project = getProjectById(id);
        return ProjectMapper.mapToResponse(project, getTotalCost(project), getProjectTasks(project));
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

    public int getTotalCost(Project project){
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
    public void generateContract(Long projectId, ContractRequest contractRequest){
        Project project = getProjectById(projectId);
        if(project.getWorkProjects().isEmpty()&&project.getMaterialProjects().isEmpty())
            throw new EstimateEmptyException("The estimate is empty, add expenses");
        if(contractRequest.getEndDate().isBefore(contractRequest.getStartDate()))
            throw new IncorrectDataException("Дата завершение работ раньше чем дата начала");
        project.setStartDate(contractRequest.getStartDate());
        project.setEndDate(contractRequest.getEndDate());
        byte[] contract = GenerateContract.generate(project, getTotalCost(projectId));
        project.setContractFile(contract);
        projectRepository.save(project);
    }

    public byte[] getContract(Long projectId){
        Project project = getProjectById(projectId);
        if(project.getContractFile()==null)
            throw new ContractHasBeenNotCreatedException("Договор еще не создан");
        return project.getContractFile();
    }

    public List<WorkProjectResponse> getFreeWorkProjects(long id){
        Project project = getProjectById(id);
        return workProjectService.getFreeWorkProject(project);
    }

    public List<Task> getProjectTasks(Project project){
        List<Task> tasks = new ArrayList<>();
        for (WorkProject workProject : project.getWorkProjects()) {
            if (workProject.getTask() != null) {
                tasks.add(workProject.getTask());
            }
        }
        return tasks;
    }

    @Transactional
    public void updateStatusProject(long id){
        Project project = getProjectById(id);
        if(project.getIsCompleted())
            throw new IncorrectDataException("Проект уже завершен");
        List<WorkProject> workProjects = project.getWorkProjects();
        workProjects.stream()
                .filter(workProject -> workProject.getTask() != null && (workProject.getTask().getTaskStatus() != TaskStatus.COMPLETED))
                .findFirst()
                .ifPresent(workProject -> {
                    throw new IncorrectDataException("Не все задачи еще завершены");
                });
        project.setIsCompleted(true);
        projectRepository.save(project);
    }


}
