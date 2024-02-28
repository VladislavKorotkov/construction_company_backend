package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Builder;
import by.bsuir.constructioncompany.services.BuilderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/builders")
public class BuilderController {
    private final BuilderService builderService;

    public BuilderController(BuilderService builderService) {
        this.builderService = builderService;
    }

    @GetMapping
    public ResponseEntity<List<Builder>> getBuilders(){
        return ResponseEntity.ok(builderService.getBuilders());
    }
}
