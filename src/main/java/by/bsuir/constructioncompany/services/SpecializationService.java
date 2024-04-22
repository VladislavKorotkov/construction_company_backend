package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Specialization;
import by.bsuir.constructioncompany.requests.SpecializationRequest;

import java.util.List;

public interface SpecializationService {
    List<Specialization> getSpecialization();
    void createSpecialization(SpecializationRequest specializationRequest);
    void updateSpecialization(Long id, SpecializationRequest specializationRequest);
    Specialization getSpecializationById(Long id);
    void deleteSpecialization(Long id);
}
