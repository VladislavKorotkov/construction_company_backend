package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.Task;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.ContractRequest;
import by.bsuir.constructioncompany.requests.MaterialProjectRequest;
import by.bsuir.constructioncompany.requests.ProjectEstimateRequest;
import by.bsuir.constructioncompany.requests.WorkProjectRequest;
import by.bsuir.constructioncompany.responses.EstimateResponse;
import by.bsuir.constructioncompany.responses.ProjectResponse;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;

import java.util.List;

public interface ProjectService {
    void createProject(Application application, User foreman);
    ProjectResponse getProject(Long id);
    List<Project> getAllProjects();
    List<Project> getAllProjectsByForeman(User foreman);
    List<Project> getAllProjectsByUser(User user);
    Project getProjectById(Long id);
    void createEstimate(Long id, ProjectEstimateRequest projectEstimateRequest);
    void createMaterialEstimate(Long id, MaterialProjectRequest materialProjectRequest);
    void createWorkEstimate(Long id, WorkProjectRequest workProjectRequest);
    EstimateResponse getEstimate(Long id);
    int getTotalCost(Long id);
    int getTotalCost(Project project);
    void deleteWorkEstimate(Long projectId, Long workId);
    void deleteMaterialEstimate(Long projectId, Long materialId);
    byte[] getEstimateXlsx(Long projectId);
    void generateContract(Long projectId, ContractRequest contractRequest);
    byte[] getContract(Long projectId);
    List<WorkProjectResponse> getFreeWorkProjects(long id);
    List<Task> getProjectTasks(Project project);
    void updateStatusProject(long id);
}
