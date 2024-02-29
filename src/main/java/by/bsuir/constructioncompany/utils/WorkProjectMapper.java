package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.MaterialProject;
import by.bsuir.constructioncompany.models.WorkProject;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;

import java.util.List;
import java.util.stream.Collectors;

public class WorkProjectMapper {
    public static List<WorkProjectResponse> mapToResponseList(List<WorkProject> workProjects) {
        return workProjects.stream()
                .map(WorkProjectMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    private static WorkProjectResponse mapToResponse(WorkProject workProject) {
        return WorkProjectResponse.builder()
                .workProjectId(workProject.getId())
                .workId(workProject.getWork().getId())
                .workName(workProject.getWork().getName())
                .cost(workProject.getCostOld())
                .quantity(workProject.getQuantity())
                .unit(workProject.getWork().getUnit().getLabel())
                .totalCost(calculateTotalCost(workProject))
                .build();
    }

    private static int calculateTotalCost(WorkProject workProject) {
        return workProject.getCostOld()*workProject.getQuantity();
    }
}
