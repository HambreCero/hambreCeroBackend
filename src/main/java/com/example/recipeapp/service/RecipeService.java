package com.example.recipeapp.service;

import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.dto.RecipeInDto;
import com.example.recipeapp.dto.RecipeOutDto;
import com.example.recipeapp.exception.RecipeNotFoundException;
import com.example.recipeapp.repository.IngredientRepository;
import com.example.recipeapp.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }
    private RecipeOutDto toOutDto(Recipe recipe) {
       RecipeOutDto out = modelMapper.map(recipe, RecipeOutDto.class);

            if (recipe.getIngredients() != null) {
                 out.ingredientIds = recipe.getIngredients()
                    .stream()
                    .map(Ingredient::getId)
                    .toList();
            }
            if (recipe.getImageName() != null && !recipe.getImageName().isBlank()) {
                 out.setImageUrl("/images/" + recipe.getImageName());
             } else {
                 out.setImageUrl(null);
        }
     return out;
    }

    public List<RecipeOutDto> findAll() {
        return recipeRepository.findAll()
                .stream()
                // .map(r -> modelMapper.map(r, RecipeOutDto.class))
                .map(this::toOutDto)
                .toList();
    }

    public RecipeOutDto create(RecipeInDto inDto) {
        Recipe recipe = new Recipe();
        recipe.setName(inDto.name);
        recipe.setDifficulty(inDto.difficulty);
        recipe.setVegetarian(inDto.vegetarian);
        recipe.setEstimatedCost(inDto.estimatedCost);
        recipe.setLastModified(inDto.lastModified);
        recipe.setServings(inDto.servings);
        recipe.setImageName(inDto.imageName);

        List<Ingredient> ingredients = ingredientRepository.findAllById(inDto.ingredientIds);

        if (ingredients.size() != inDto.ingredientIds.size()) {
            throw new IllegalArgumentException("Some ingredientIds do not exist");
        }

        recipe.setIngredients(ingredients);
        Recipe saved = recipeRepository.save(recipe);
        // return modelMapper.map(saved, RecipeOutDto.class);
        return toOutDto(saved);

    }
    public RecipeOutDto findById(Long id) {
            Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RecipeNotFoundException(id));
            // return modelMapper.map(recipe, RecipeOutDto.class);
            return toOutDto(recipe);

    }
    public RecipeOutDto update(Long id, RecipeInDto inDto) {

        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RecipeNotFoundException(id));

            recipe.setName(inDto.name);
            recipe.setDifficulty(inDto.difficulty);
            recipe.setVegetarian(inDto.vegetarian);
            recipe.setEstimatedCost(inDto.estimatedCost);
            recipe.setLastModified(inDto.lastModified);
            recipe.setServings(inDto.servings);
            recipe.setImageName(inDto.imageName);
        List<Ingredient> ingredients = ingredientRepository.findAllById(inDto.ingredientIds);
        if (ingredients.size() != inDto.ingredientIds.size()) {
            throw new IllegalArgumentException("Some ingredientIds do not exist");
        }
        recipe.setIngredients(ingredients);

        Recipe saved = recipeRepository.save(recipe);
        return toOutDto(saved);
        // return modelMapper.map(saved, RecipeOutDto.class);
    }
    
    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RecipeNotFoundException(id));

            recipeRepository.delete(recipe);
    }
}
