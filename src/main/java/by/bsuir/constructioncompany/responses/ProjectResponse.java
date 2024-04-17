package by.bsuir.constructioncompany.responses;

import by.bsuir.constructioncompany.models.Address;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address address;
    private String builderName;
    private Boolean isCompleted;
    private int cost;
    private List<TaskResponse> tasks;
}
