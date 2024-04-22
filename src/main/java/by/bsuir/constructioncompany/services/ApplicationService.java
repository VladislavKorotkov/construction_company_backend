package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.requests.ApplicationRequest;

import java.util.List;

public interface ApplicationService {
    void createApplication(ApplicationRequest applicationRequest, User user);
    void deleteApplication(Long id);
    List<Application> getApplications();
    List<Application> getApplicationsByUser(User user);
    Application getApplicationById(Long id);
    void acceptTheApplication(Long id, User foreman);
}
