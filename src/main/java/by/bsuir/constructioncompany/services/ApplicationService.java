package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Address;
import by.bsuir.constructioncompany.models.Application;
import by.bsuir.constructioncompany.models.User;
import by.bsuir.constructioncompany.repositories.ApplicationRepository;
import by.bsuir.constructioncompany.requests.ApplicationRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    public ApplicationService(ApplicationRepository applicationRepository, AddressService addressService, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
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
    public void deleteApplication(Application application){
        applicationRepository.delete(application);
    }

    public List<Application> getApplications(){
        return applicationRepository.findAll();
    }

    public List<Application> getApplicationsByUser(User user){
        return applicationRepository.getApplicationByUser(user);
    }
}
