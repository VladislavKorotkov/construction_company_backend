package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.requests.MaterialProjectRequest;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;

import java.util.List;

public interface MaterialProjectService {
    void createMaterialProjects(List<MaterialProjectRequest> materialProjectRequests, Project project);
    void createMaterialProject(MaterialProjectRequest materialProjectRequest, Project project);
    List<MaterialProjectResponse> getMaterialProjectResponses(Project project);
    void deleteMaterialProject(Project project, Long materialId);
}
