package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Work;
import by.bsuir.constructioncompany.requests.WorkRequest;
import by.bsuir.constructioncompany.services.WorkService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
public class WorkController {
    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping
    public ResponseEntity<List<Work>> getWorks(){
        return ResponseEntity.ok(workService.getWorks());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Work>> getAvailableWorks(){
        return ResponseEntity.ok(workService.getAvailableWorks());
    }

    @PostMapping
    public ResponseEntity<String> createWork(@RequestBody @Valid WorkRequest workRequest){
        workService.createWork(workRequest);
        return ResponseEntity.ok("Услуга добавлена");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWork(@PathVariable("id") Long id){
        workService.deleteWork(id);
        return ResponseEntity.ok("Услуга удалена");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateWork(@PathVariable("id") Long id, @RequestBody WorkRequest workRequest){
        workService.updateWork(id, workRequest);
        return ResponseEntity.ok("Услуга изменена");
    }


}
