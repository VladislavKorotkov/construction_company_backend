package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.Task;
import by.bsuir.constructioncompany.responses.TaskResponse;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
        public static List<TaskResponse> mapToResponseList(List<Task> tasks){
            return tasks.stream()
                    .map(TaskMapper::mapToResponse)
                    .collect(Collectors.toList());
        }
        public static TaskResponse mapToResponse(Task task){
            return TaskResponse.builder()
                    .workProjectId(task.getWorkProject().getId())
                    .description(task.getDescription())
                    .status(task.getTaskStatus().getLabel())
                    .projectId(task.getWorkProject().getProject().getId())
                    .id(task.getId())
                    .builderName(task.getBuilder().getUser().getSurname()+" "+task.getBuilder().getUser().getName())
                    .name(task.getWorkProject().getWork().getName())
                    .build();
        }
}
