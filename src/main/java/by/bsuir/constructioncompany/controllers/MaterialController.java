package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.models.Material;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    @GetMapping
    public ResponseEntity<List<Material>> getMaterials(){
        return null;
    }
 }
