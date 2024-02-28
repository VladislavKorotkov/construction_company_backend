package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Material;
import by.bsuir.constructioncompany.requests.MaterialRequest;
import by.bsuir.constructioncompany.services.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<Material>> getMaterials(){
        return ResponseEntity.ok(materialService.getMaterials());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Material>> getAvailableMaterials(){
        return ResponseEntity.ok(materialService.getAvailableMaterials());
    }

    @PostMapping
    public ResponseEntity<String> createMaterial(@RequestBody @Valid MaterialRequest materialRequest){
        materialService.createMaterial(materialRequest);
        return ResponseEntity.ok("Материал добавлен");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMaterial(@RequestBody MaterialRequest materialRequest, @PathVariable("id") Long id){
        materialService.updateMaterial(id, materialRequest);
        return ResponseEntity.ok("Материал изменен");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable("id") Long id){
        materialService.deleteMaterial(id);
        return ResponseEntity.ok("Материал удален");
    }
 }
