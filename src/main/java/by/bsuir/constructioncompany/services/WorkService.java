package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Work;
import by.bsuir.constructioncompany.requests.WorkRequest;

import java.util.List;

public interface WorkService {
    List<Work> getWorks();
    List<Work> getAvailableWorks();
    Work getWorkById(Long id);
    void createWork(WorkRequest workRequest);
    void deleteWork(Long id);
    void updateWork(Long id, WorkRequest workRequest);
}
