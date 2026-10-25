package org.telecom.sigarh.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telecom.sigarh.entities.Categorie;
import org.telecom.sigarh.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categorieService;

    @PostMapping("/addCategorie/{corpsId}")
    public ResponseEntity<Categorie> addCategorie(@RequestBody Categorie categorie,@PathVariable Long corpsId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categorieService.addCategorie(categorie, corpsId));

    }

    @PatchMapping("/updateCategorie/{id}")
    public ResponseEntity<Categorie> updateCategorie(
            @PathVariable Long id,
            @RequestBody Categorie categorie) {

        return ResponseEntity.ok(
                categorieService.updateCategorie(id, categorie));

    }

    @GetMapping("/corps/{id}")
    public ResponseEntity<List<Categorie>> getCategoriesByCorps(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                categorieService.getCategoriesByCorps(id));

    }
}
