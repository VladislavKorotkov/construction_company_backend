package by.bsuir.constructioncompany.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {
    private String description;
    private String status;
    private long id;
    private long workProjectId;
    private long projectId;
    private String builderName;
    private String name;
}
