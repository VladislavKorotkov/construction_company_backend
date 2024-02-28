package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Specialization;
import by.bsuir.constructioncompany.requests.SpecializationRequest;
import by.bsuir.constructioncompany.services.SpecializationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {
    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping
    public ResponseEntity<List<Specialization>> getSpecializations(){
        return ResponseEntity.ok(specializationService.getSpecialization());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable("id") Long id){
        specializationService.deleteSpecialization(id);
        return ResponseEntity.ok("Специальность удалена");
    }

    @PostMapping
    public ResponseEntity<String> createSpecialization(@RequestBody @Valid SpecializationRequest specializationRequest){
        specializationService.createSpecialization(specializationRequest);
        return ResponseEntity.ok("Специльность добавлена");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSpecialization(@PathVariable("id") Long id, SpecializationRequest specializationRequest){
        specializationService.updateSpecialization(id, specializationRequest);
        return ResponseEntity.ok("Специальность обновлена");
    }

}
