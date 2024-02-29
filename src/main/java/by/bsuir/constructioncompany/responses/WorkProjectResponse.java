package by.bsuir.constructioncompany.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkProjectResponse {
    private Long workProjectId;
    private Long workId;
    private String workName;
    private int cost;
    private int quantity;
    private int totalCost;
    private String unit;
}
