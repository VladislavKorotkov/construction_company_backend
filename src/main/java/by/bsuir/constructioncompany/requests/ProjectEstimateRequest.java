package by.bsuir.constructioncompany.requests;

import lombok.Data;

import java.util.List;

@Data
public class ProjectEstimateRequest {
    private List<MaterialProjectRequest> materials;
    private List<WorkProjectRequest> works;
}
