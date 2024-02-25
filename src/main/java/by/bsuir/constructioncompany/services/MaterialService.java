package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.exceptions.CannotBeDeletedException;
import by.bsuir.constructioncompany.exceptions.ObjectNotFoundException;
import by.bsuir.constructioncompany.models.Address;
import by.bsuir.constructioncompany.models.Material;
import by.bsuir.constructioncompany.repositories.MaterialRepository;
import by.bsuir.constructioncompany.requests.MaterialRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    public MaterialService(MaterialRepository materialRepository, ModelMapper modelMapper) {
        this.materialRepository = materialRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void createMaterial(MaterialRequest materialRequest){
        Material material = modelMapper.map(materialRequest, Material.class);
        materialRepository.save(material);
    }

    public List<Material> getAvailableMaterials(){
        return materialRepository.findByIsAvailableTrue();
    }

    public List<Material> getMaterials(){
        return materialRepository.findAll();
    }

    public Material getMaterialById(Long id){
       return materialRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Материал не найден"));
    }

    @Transactional
    public void updateMaterial(Long id, MaterialRequest materialRequest){
        Material material = getMaterialById(id);
        modelMapper.map(materialRequest, material);
        materialRepository.save(material);
    }

    @Transactional
    public void deleteMaterial(Long id){
        Material material = getMaterialById(id);
        if(material.getMaterialProjects().isEmpty())
            materialRepository.delete(material);
        else
            throw new CannotBeDeletedException("Невозможно удалить материал, так как он включен в смету");
    }
}
