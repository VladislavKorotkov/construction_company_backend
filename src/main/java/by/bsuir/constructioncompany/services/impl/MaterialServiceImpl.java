package by.bsuir.constructioncompany.services.impl;

import by.bsuir.constructioncompany.exceptions.CannotBeDeletedException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Material;
import by.bsuir.constructioncompany.repositories.MaterialRepository;
import by.bsuir.constructioncompany.requests.MaterialRequest;
import by.bsuir.constructioncompany.services.MaterialService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    public MaterialServiceImpl(MaterialRepository materialRepository, ModelMapper modelMapper) {
        this.materialRepository = materialRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    @Transactional
    public void createMaterial(MaterialRequest materialRequest){
        Material material = modelMapper.map(materialRequest, Material.class);
        materialRepository.save(material);
    }
    @Override
    public List<Material> getAvailableMaterials(){
        return materialRepository.findByIsAvailableTrue();
    }
    @Override
    public List<Material> getMaterials(){
        return materialRepository.findAll();
    }
    @Override
    public Material getMaterialById(Long id){
       return materialRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Материал не найден"));
    }
    @Override
    @Transactional
    public void updateMaterial(Long id, MaterialRequest materialRequest){
        Material material = getMaterialById(id);
        modelMapper.map(materialRequest, material);
        materialRepository.save(material);
    }
    @Override
    @Transactional
    public void deleteMaterial(Long id){
        Material material = getMaterialById(id);
        if(material.getMaterialProjects().isEmpty())
            materialRepository.delete(material);
        else
            throw new CannotBeDeletedException("Невозможно удалить материал, так как он включен в смету");
    }
}
