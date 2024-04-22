package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Task;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.TaskRequest;
import by.bsuir.constructioncompany.requests.TaskStatusChangeRequest;
import by.bsuir.constructioncompany.responses.TaskResponse;

import java.util.List;

public interface TaskService {
    void createTask(TaskRequest taskRequest, long projectId);
    Task getTask(long id);
    void deleteTask(long projectId, long taskId);
    List<TaskResponse> getTasksBuilder(User user);
    void changeTaskStatus(TaskStatusChangeRequest taskStatusChangeRequest, long id);
}
