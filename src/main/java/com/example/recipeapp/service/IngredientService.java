package com.example.recipeapp.service;

import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.dto.IngredientInDto;
import com.example.recipeapp.dto.IngredientOutDto;
import com.example.recipeapp.exception.IngredientNotFoundException;
import com.example.recipeapp.repository.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.recipeapp.domain.Season;


import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    public IngredientService(IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    public IngredientOutDto create(IngredientInDto inDto) {
        Ingredient ingredient = new Ingredient();

        ingredient.setName(inDto.name);
        ingredient.setCalories(inDto.calories);
        ingredient.setOrganic(inDto.isOrganic);
        ingredient.setHarvestDate(inDto.harvestDate);
        ingredient.setPriceKg(inDto.priceKg);
        ingredient.setCarbonFootprint(inDto.carbonFootprint);

        // season: String -> enum
        ingredient.setSeason(Season.valueOf(inDto.season.toUpperCase()));

        Ingredient saved = ingredientRepository.save(ingredient);

        IngredientOutDto out = modelMapper.map(saved, IngredientOutDto.class);

        if (saved.getSeason() != null) {
            out.setSeason(saved.getSeason().name());
        }

        return out;
    }

    public List<IngredientOutDto> findAll() {
        return ingredientRepository.findAll().stream().map(i -> {
            IngredientOutDto out = modelMapper.map(i, IngredientOutDto.class);

            if (i.getSeason() != null) {
                out.setSeason(i.getSeason().name());
            }

            return out;
        }).toList();
    }
    
    public IngredientOutDto findById(Long id){
            Ingredient ingredient = ingredientRepository.findById(id)
            .orElseThrow(() -> new IngredientNotFoundException(id));

            IngredientOutDto out = modelMapper.map(ingredient, IngredientOutDto.class);

        if (ingredient.getSeason() != null) {
            out.setSeason(ingredient.getSeason().name());
        }

        return out;
    }

    public IngredientOutDto update(Long id, IngredientInDto inDto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));

        ingredient.setName(inDto.name);
        ingredient.setCalories(inDto.calories);
        ingredient.setOrganic(inDto.isOrganic);
        ingredient.setHarvestDate(inDto.harvestDate);
        ingredient.setPriceKg(inDto.priceKg);
        ingredient.setCarbonFootprint(inDto.carbonFootprint);

        ingredient.setSeason(Season.valueOf(inDto.season.toUpperCase()));

        Ingredient saved = ingredientRepository.save(ingredient);

         IngredientOutDto out = modelMapper.map(saved, IngredientOutDto.class);
            if (saved.getSeason() != null) {
                out.setSeason(saved.getSeason().name());
            }
         return out;
    }
    public void delete(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));

        ingredientRepository.delete(ingredient);
    }

}
