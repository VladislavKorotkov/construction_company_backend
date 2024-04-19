package by.bsuir.constructioncompany.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ContractRequest {
    @NotNull(message = "Начало работ не может быть пустым")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Завершение работ не может быть пустым")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
