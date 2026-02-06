package com.example.recipeapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IngredientInDto {

    @NotBlank(message = "Ingredient name cannot be blank")
    public String name;

    @Min(value = 0, message = "Calories must be a positive number")
    public int calories;

    @NotNull(message = "Season cannot be null")
    public String season; // "SPRING", "SUMMER", "AUTUMN", "WINTER"

    public boolean isOrganic;

    @NotNull(message = "Harvest date cannot be null")
    public LocalDate harvestDate;

    @NotNull(message = "Price per kg cannot be null")
    @DecimalMin(value = "0.0", message = "Price per kg must be positive")
    public BigDecimal priceKg;

    @NotNull(message = "Carbon foot print cannot be null")
    @DecimalMin(value = "0.0", message = "Carbon foot print must be positive")
    public BigDecimal carbonFootprint;
}
