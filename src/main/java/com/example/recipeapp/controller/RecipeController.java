package com.example.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import com.example.recipeapp.dto.RecipeOutDto;
import com.example.recipeapp.service.RecipeService;

import org.springframework.http.ResponseEntity;
import com.example.recipeapp.dto.RecipeInDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/{id}")
    public RecipeOutDto getRecipeById(@PathVariable Long id) {
        log.info("GET /recipes/{} - fetching recipe", id);
        RecipeOutDto recipe = recipeService.findById(id);
        log.info("GET /recipes/{} - recipe found", id);
        return recipe;
    }

    @GetMapping("/recipes")
    public List<RecipeOutDto> getAllRecipes() {
        log.info("GET /recipes - fetching all recipes");
        List<RecipeOutDto> recipes = recipeService.findAll();
        log.info("GET /recipes - returned {} recipes", recipes.size());
        return recipes;
    }

  @PostMapping("/recipes")
    public RecipeOutDto createRecipe(@Valid @RequestBody RecipeInDto inDto) {
        log.info("POST /recipes - creating recipe name='{}' ingredientIds={}",
                inDto.name, inDto.ingredientIds);
        RecipeOutDto created = recipeService.create(inDto);
        log.info("POST /recipes - recipe created");
        return created;
    }

    @PutMapping("/recipes/{id}")
    public RecipeOutDto updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeInDto inDto) {
        log.info("PUT /recipes/{} - updating recipe name='{}' ingredientIds={}",
            id, inDto.name, inDto.ingredientIds);
        RecipeOutDto updated = recipeService.update(id, inDto);
        log.info("PUT /recipes/{} - recipe updated", id);
        return updated;
    }


    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        log.info("DELETE /recipes/{} - deleting recipe", id);
        recipeService.delete(id);
        log.info("DELETE /recipes/{} - recipe deleted", id);
        return ResponseEntity.noContent().build(); // 204
    }
}
