package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.models.MaterialProject;
import by.bsuir.constructioncompany.responses.MaterialProjectResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MaterialProjectMapper {
    public static List<MaterialProjectResponse> mapToResponseList(List<MaterialProject> materialProjects) {
        return materialProjects.stream()
                .map(MaterialProjectMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    private static MaterialProjectResponse mapToResponse(MaterialProject materialProject) {
        return MaterialProjectResponse.builder()
                .materialProjectId(materialProject.getId())
                .materialId(materialProject.getMaterial().getId())
                .materialName(materialProject.getMaterial().getName())
                .cost(materialProject.getCostOld())
                .quantity(materialProject.getQuantity())
                .unit(materialProject.getMaterial().getUnit().getLabel())
                .totalCost(calculateTotalCost(materialProject))
                .build();
    }

    private static int calculateTotalCost(MaterialProject materialProject) {
        return materialProject.getCostOld()*materialProject.getQuantity();
    }
}