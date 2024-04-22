package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Material;
import by.bsuir.constructioncompany.models.MaterialProject;
import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.repositories.MaterialProjectRepository;
import by.bsuir.constructioncompany.requests.MaterialProjectRequest;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;
import by.bsuir.constructioncompany.services.MaterialProjectService;
import by.bsuir.constructioncompany.services.MaterialService;
import by.bsuir.constructioncompany.utils.MaterialProjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialProjectServiceImpl implements MaterialProjectService {
    private final MaterialProjectRepository materialProjectRepository;
    private final MaterialService materialService;
    public MaterialProjectServiceImpl(MaterialProjectRepository materialProjectRepository, MaterialService materialService) {
        this.materialProjectRepository = materialProjectRepository;
        this.materialService = materialService;
    }
    @Override
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
    @Override
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
    @Override
    public List<MaterialProjectResponse> getMaterialProjectResponses(Project project){
        List<MaterialProject> materialProjects = materialProjectRepository.getMaterialProjectByProject(project);
        return MaterialProjectMapper.mapToResponseList(materialProjects);
    }
    @Override
    public void deleteMaterialProject(Project project, Long materialId){
        MaterialProject materialProject = materialProjectRepository.findByProjectAndId(project,materialId).orElseThrow(()->new ObjectNotFoundException("Материал не найден"));
        materialProjectRepository.delete(materialProject);
    }
}
