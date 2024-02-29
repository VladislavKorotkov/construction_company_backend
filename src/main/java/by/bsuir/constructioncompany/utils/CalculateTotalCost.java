package by.bsuir.constructioncompany.utils;

import by.bsuir.constructioncompany.responses.MaterialProjectResponse;
import by.bsuir.constructioncompany.responses.WorkProjectResponse;

import java.util.List;

public class CalculateTotalCost {
    public static int calculate(List<WorkProjectResponse> workProjectResponses, List<MaterialProjectResponse> materialProjectResponses){
        int totalCost = materialProjectResponses.stream()
                .mapToInt(MaterialProjectResponse::getTotalCost)
                .sum();

        totalCost += workProjectResponses.stream()
                .mapToInt(WorkProjectResponse::getTotalCost)
                .sum();
        return totalCost;
    }
}
