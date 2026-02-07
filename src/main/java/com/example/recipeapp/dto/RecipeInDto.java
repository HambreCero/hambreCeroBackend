package com.example.recipeapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RecipeInDto {

    @NotBlank(message = "Recipe name cannot be blank")
    public String name;

    @Min(value = 1, message = "Difficulty must be between 1 and 5")
    public int difficulty;

    public boolean vegetarian;

    @NotNull(message = "Estimated cost cannot be null")
    @DecimalMin(value = "0.0", message = "Cost must be positive")
    public BigDecimal estimatedCost;

    @NotNull(message = "Last modified cannot be null")
    public LocalDate lastModified;

    @Min(value = 1, message = "Servings must be at least 1")
    public int servings;

    @NotNull(message = "ingredientIds cannot be null")
    public List<Long> ingredientIds;

    public String imageName;

}
