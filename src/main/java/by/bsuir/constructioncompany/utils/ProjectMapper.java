package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.Project;
import by.bsuir.constructioncompany.models.Task;
import by.bsuir.constructioncompany.responses.ProjectResponse;
import by.bsuir.constructioncompany.responses.TaskResponse;

import java.util.List;

public class ProjectMapper {
    public static ProjectResponse mapToResponse(Project project, int cost, List<Task> tasks){
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .address(project.getAddress())
                .isCompleted(project.getIsCompleted())
                .builderName(project.getForeman().getSurname()+ " " + project.getForeman().getName())
                .tasks(TaskMapper.mapToResponseList(tasks))
                .cost(cost)
                .build();
    }
}
