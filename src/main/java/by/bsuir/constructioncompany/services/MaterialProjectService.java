package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Material;
import by.bsuir.constructioncompany.models.MaterialProject;
import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.repositories.MaterialProjectRepository;
import by.bsuir.constructioncompany.requests.MaterialProjectRequest;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;
import by.bsuir.constructioncompany.utils.MaterialProjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialProjectService {
    private final MaterialProjectRepository materialProjectRepository;
    private final MaterialService materialService;
    public MaterialProjectService(MaterialProjectRepository materialProjectRepository, MaterialService materialService) {
        this.materialProjectRepository = materialProjectRepository;
        this.materialService = materialService;
    }
    @Transactional
    public void createMaterialProjects(List<MaterialProjectRequest> materialProjectRequests, Project project){
        for(MaterialProjectRequest materialProjectRequest : materialProjectRequests){
            Material material = materialService.getMaterialById(materialProjectRequest.getMaterialId());
            if(material.getIsAvailable()){
                MaterialProject materialProject = MaterialProject.builder()
                        .material(material)
                        .costOld(material.getCost())
                        .project(project)
                        .quantity(materialProjectRequest.getQuantity())
                        .build();
                materialProjectRepository.save(materialProject);
            }
        }
    }

    @Transactional
    public void createMaterialProject(MaterialProjectRequest materialProjectRequest, Project project){
            Material material = materialService.getMaterialById(materialProjectRequest.getMaterialId());
            if(material.getIsAvailable()){
                MaterialProject materialProject = MaterialProject.builder()
                        .material(material)
                        .costOld(material.getCost())
                        .project(project)
                        .quantity(materialProjectRequest.getQuantity())
                        .build();
                materialProjectRepository.save(materialProject);
            }
    }

    public List<MaterialProjectResponse> getMaterialProjectResponses(Project project){
        List<MaterialProject> materialProjects = materialProjectRepository.getMaterialProjectByProject(project);
        return MaterialProjectMapper.mapToResponseList(materialProjects);
    }

    public void deleteMaterialProject(Project project, Long materialId){
        MaterialProject materialProject = materialProjectRepository.findByProjectAndId(project,materialId).orElseThrow(()->new ObjectNotFoundException("Материал не найден"));
        materialProjectRepository.delete(materialProject);
    }
}
