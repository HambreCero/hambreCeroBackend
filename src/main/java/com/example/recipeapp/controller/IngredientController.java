package com.example.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import com.example.recipeapp.dto.IngredientInDto;
import com.example.recipeapp.dto.IngredientOutDto;
import com.example.recipeapp.service.IngredientService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/ingredients")
    public ResponseEntity<IngredientOutDto> createIngredient(@Valid @RequestBody IngredientInDto inDto) {
        log.info("POST /ingredients - creating ingredient");
        IngredientOutDto created = ingredientService.create(inDto);
        log.info("POST /ingredients - ingredient created");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/ingredients")
    public List<IngredientOutDto> getAllIngredients() {
        log.info("GET /ingredients - fetching all ingredients");
        List<IngredientOutDto> ingredients = ingredientService.findAll();
        log.info("GET /ingredients - returned {} ingredients", ingredients.size());
        return ingredients;
    }

    @GetMapping("/ingredients/{id}")
    public IngredientOutDto getIngredient(@PathVariable Long id) {
        log.info("GET /ingredients/{} - fetching ingredient", id);
        IngredientOutDto ingredient = ingredientService.findById(id);
        log.info("GET /ingredients/{} - ingredient found", id);
        return ingredient;
    }

    @PutMapping("/ingredients/{id}")
    public ResponseEntity<IngredientOutDto> updateIngredient(@PathVariable Long id, @Valid @RequestBody IngredientInDto inDto) {
        log.info("PUT /ingredients/{} - updating ingredient", id);
        IngredientOutDto updated = ingredientService.update(id, inDto);
        log.info("PUT /ingredients/{} - ingredient updated", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        log.info("DELETE /ingredients/{} - deleting ingredient", id);
        ingredientService.delete(id);
        log.info("DELETE /ingredients/{} - ingredient deleted", id);
        return ResponseEntity.noContent().build(); // 204
    }
}
