package by.bsuir.constructioncompany.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialProjectResponse {
    private Long materialProjectId;
    private Long materialId;
    private String materialName;
    private int cost;
    private int quantity;
    private int totalCost;
    private String unit;
}
