package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.CannotBeDeletedException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Work;
import by.bsuir.constructioncompany.repositories.WorkRepository;
import by.bsuir.constructioncompany.requests.WorkRequest;
import by.bsuir.constructioncompany.services.WorkService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    public final WorkRepository workRepository;
    public final ModelMapper modelMapper;

    public WorkServiceImpl(WorkRepository workRepository, ModelMapper modelMapper) {
        this.workRepository = workRepository;
        this.modelMapper = modelMapper;
    }

    public List<Work> getWorks(){
        return workRepository.findAll();
    }

    public List<Work> getAvailableWorks(){
        return workRepository.findByIsAvailableTrue();
    }

    public  Work getWorkById(Long id){
        return workRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Услуга не найдена"));
    }

    @Transactional
    public void createWork(WorkRequest workRequest){
        Work work = modelMapper.map(workRequest, Work.class);
        workRepository.save(work);
    }

    @Transactional
    public void deleteWork(Long id){
        Work work = getWorkById(id);
        if(work.getWorkProjects().isEmpty())
            workRepository.delete(work);
        else
            throw new CannotBeDeletedException("Невозможно удалить услугу, так как она включена в смету");
    }

    @Transactional
    public void updateWork(Long id, WorkRequest workRequest){
        Work work = getWorkById(id);
        modelMapper.map(workRequest, work);
        workRepository.save(work);
    }
}
