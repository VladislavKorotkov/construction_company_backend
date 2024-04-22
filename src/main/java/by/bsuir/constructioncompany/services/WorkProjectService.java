package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.WorkProject;
import by.bsuir.constructioncompany.requests.WorkProjectRequest;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;

import java.util.List;

public interface WorkProjectService {
    void createWorkProjects(List<WorkProjectRequest> workProjectRequests, Project project);
    void createWorkProject(WorkProjectRequest workProjectRequest, Project project);
    List<WorkProjectResponse> getWorkProjectResponses(Project project);
    void deleteWorkEstimate(Project project, Long id);
    List<WorkProjectResponse> getFreeWorkProject(Project project);
    WorkProject getWorkProject(long id);
}
