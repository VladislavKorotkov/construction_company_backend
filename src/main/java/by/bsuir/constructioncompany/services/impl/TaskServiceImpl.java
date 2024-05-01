package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.IncorrectDataException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.*;
import by.bsuir.constructioncompany.models.enums.TaskStatus;
import by.bsuir.constructioncompany.repositories.TaskRepository;
import by.bsuir.constructioncompany.requests.TaskRequest;
import by.bsuir.constructioncompany.requests.TaskStatusChangeRequest;
import by.bsuir.constructioncompany.responses.TaskResponse;
import by.bsuir.constructioncompany.services.BuilderService;
import by.bsuir.constructioncompany.services.ProjectService;
import by.bsuir.constructioncompany.services.TaskService;
import by.bsuir.constructioncompany.services.WorkProjectService;
import by.bsuir.constructioncompany.utils.TaskMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final BuilderService builderService;

    private final ProjectService projectService;
    private final WorkProjectService workProjectService;

    public TaskServiceImpl(TaskRepository taskRepository, BuilderService builderService, ProjectService projectService, WorkProjectService workProjectService) {
        this.taskRepository = taskRepository;
        this.builderService = builderService;
        this.projectService = projectService;
        this.workProjectService = workProjectService;
    }

    @Transactional
    public void createTask(TaskRequest taskRequest, long projectId){
        Project project = projectService.getProjectById(projectId);
        Builder builder = builderService.getBuilder(taskRequest.getBuilderId());
        WorkProject workProject = workProjectService.getWorkProject(taskRequest.getWorkProjectId());
        if(!project.getId().equals(workProject.getProject().getId()))
            throw new IncorrectDataException("Данная работа не принадлежит данному проекту");
        if(workProject.getTask()!=null)
            throw new IncorrectDataException("На работу уже назначен строитель");
        Task task = Task.builder()
                .description(taskRequest.getDescription())
                .workProject(workProject)
                .builder(builder)
                .build();
        taskRepository.save(task);
    }

    public Task getTask(long id){
        return taskRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Задача не найдена"));
    }

    @Transactional
    public void deleteTask(long projectId, long taskId){
        Project project = projectService.getProjectById(projectId);
        Task task = getTask(taskId);
        if(!project.getId().equals(task.getWorkProject().getProject().getId()))
            throw new IncorrectDataException("Задача не принадлежит данному проекту");
        taskRepository.delete(task);
    }

    @Transactional
    public List<TaskResponse> getTasksBuilder(User user){
        List<Task> tasks = builderService.getBuilder(user).getTasks();
        List<Task> taskAvailable = tasks.stream().filter(task->task.getTaskStatus().equals(TaskStatus.PENDING)).collect(Collectors.toList());
        return TaskMapper.mapToResponseList(taskAvailable);
    }

    @Transactional
    public void changeTaskStatus(TaskStatusChangeRequest taskStatusChangeRequest, long id){
        Task task = getTask(id);
        task.setTaskStatus(taskStatusChangeRequest.getTaskStatus());
        taskRepository.save(task);
    }

}
