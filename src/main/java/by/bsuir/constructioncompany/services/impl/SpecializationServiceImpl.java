package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.CannotBeDeletedException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Specialization;
import by.bsuir.constructioncompany.repositories.SpecializationRepository;
import by.bsuir.constructioncompany.requests.SpecializationRequest;
import by.bsuir.constructioncompany.services.SpecializationService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationRepository specializationRepository;
    private final ModelMapper modelMapper;

    public SpecializationServiceImpl(SpecializationRepository specializationRepository, ModelMapper modelMapper) {
        this.specializationRepository = specializationRepository;
        this.modelMapper = modelMapper;
    }

    public List<Specialization> getSpecialization(){
        return specializationRepository.findAll();
    }
    @Override
    @Transactional
    public void createSpecialization(SpecializationRequest specializationRequest){
        Specialization specialization = modelMapper.map(specializationRequest, Specialization.class);
        specializationRepository.save(specialization);
    }
    @Override
    @Transactional
    public void updateSpecialization(Long id, SpecializationRequest specializationRequest){
        Specialization specialization = getSpecializationById(id);
        modelMapper.map(specializationRequest, specialization);
        specializationRepository.save(specialization);
    }
    @Override
    public Specialization getSpecializationById(Long id){
        return specializationRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Специальность не найдена"));
    }
    @Override
    @Transactional
    public void deleteSpecialization(Long id){
        Specialization specialization = getSpecializationById(id);
        if(specialization.getBuilderList().isEmpty())
            specializationRepository.delete(specialization);
        else
            throw new CannotBeDeletedException("Невозможно удалить специальность, так как она используется");
    }
}
