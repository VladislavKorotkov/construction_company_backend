package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Address;
import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.ApplicationRepository;
import by.bsuir.constructioncompany.requests.ApplicationRequest;
import by.bsuir.constructioncompany.services.AddressService;
import by.bsuir.constructioncompany.services.ApplicationService;
import by.bsuir.constructioncompany.services.ProjectService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, AddressService addressService, ModelMapper modelMapper, ProjectService projectService) {
        this.applicationRepository = applicationRepository;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
        this.projectService = projectService;
    }

    @Transactional
    public void createApplication(ApplicationRequest applicationRequest, User user){
        Address address = modelMapper.map(applicationRequest.getAddress(), Address.class);
        address = addressService.createAddress(address);
        Application application = modelMapper.map(applicationRequest, Application.class);
        application.setAddress(address);
        application.setUser(user);
        applicationRepository.save(application);
    }

    @Transactional
    public void deleteApplication(Long id){
        Application application = getApplicationById(id);
        applicationRepository.delete(application);
    }

    public List<Application> getApplications(){
        return applicationRepository.findAll();
    }

    public List<Application> getApplicationsByUser(User user){
        return applicationRepository.getApplicationByUser(user);
    }

    public Application getApplicationById(Long id){
        return applicationRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Заявка не найдена"));
    }

    @Transactional
    public void acceptTheApplication(Long id, User foreman){
        Application application = getApplicationById(id);
        projectService.createProject(application, foreman);
        applicationRepository.delete(application);
    }
}
