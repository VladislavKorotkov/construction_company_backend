package by.bsuir.constructioncompany.services;

import by.bsuir.constructioncompany.models.Material;
import by.bsuir.constructioncompany.requests.MaterialRequest;

import java.util.List;

public interface MaterialService {
    void createMaterial(MaterialRequest materialRequest);
    List<Material> getAvailableMaterials();
    List<Material> getMaterials();
    Material getMaterialById(Long id);
    void updateMaterial(Long id, MaterialRequest materialRequest);
    void deleteMaterial(Long id);
}
