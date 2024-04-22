package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.Work;
import by.bsuir.constructioncompany.models.WorkProject;
import by.bsuir.constructioncompany.repositories.WorkProjectRepository;
import by.bsuir.constructioncompany.requests.WorkProjectRequest;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;
import by.bsuir.constructioncompany.services.WorkProjectService;
import by.bsuir.constructioncompany.services.WorkService;
import by.bsuir.constructioncompany.utils.WorkProjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkProjectServiceImpl implements WorkProjectService {
    private final WorkProjectRepository workProjectRepository;
    private final WorkService workService;
    public WorkProjectServiceImpl(WorkProjectRepository workProjectRepository, WorkService workService) {
        this.workProjectRepository = workProjectRepository;
        this.workService = workService;
    }
    @Override
    @Transactional
    public void createWorkProjects(List<WorkProjectRequest> workProjectRequests, Project project){
        for(WorkProjectRequest workProjectRequest: workProjectRequests){
            Work work = workService.getWorkById(workProjectRequest.getWorkId());
            if(work.getIsAvailable()){
                WorkProject workProject = WorkProject.builder()
                        .work(work)
                        .project(project)
                        .costOld(work.getCost())
                        .quantity(workProjectRequest.getQuantity())
                        .build();
                workProjectRepository.save(workProject);
            }
        }
    }
    @Override
    @Transactional
    public void createWorkProject(WorkProjectRequest workProjectRequest, Project project){
        Work work = workService.getWorkById(workProjectRequest.getWorkId());
        if(work.getIsAvailable()){
            WorkProject workProject = WorkProject.builder()
                    .work(work)
                    .project(project)
                    .costOld(work.getCost())
                    .quantity(workProjectRequest.getQuantity())
                    .build();
            workProjectRepository.save(workProject);
        }
    }
    @Override
    public List<WorkProjectResponse> getWorkProjectResponses(Project project){
        List<WorkProject> workProjects = workProjectRepository.findByProject(project);
        return WorkProjectMapper.mapToResponseList(workProjects);
    }
    @Override
    @Transactional
    public void deleteWorkEstimate(Project project, Long id){
        WorkProject workProject = workProjectRepository.findByProjectAndId(project, id).orElseThrow(()->new ObjectNotFoundException("Услуга не найдена"));
        workProjectRepository.delete(workProject);
    }
    @Override
    public List<WorkProjectResponse> getFreeWorkProject(Project project){
        return WorkProjectMapper.mapToResponseList(workProjectRepository.findByProjectAndTaskIsNull(project));
    }
    @Override
    public WorkProject getWorkProject(long id){
        return workProjectRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Работа не найден"));
    }
}
