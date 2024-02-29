package by.bsuir.constructioncompany.responses;


import by.bsuir.constructioncompany.models.Material;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EstimateResponse {
    private Long projectId;
    private int totalCost;
    private List<MaterialProjectResponse> materials;
    private List<WorkProjectResponse> works;
}
