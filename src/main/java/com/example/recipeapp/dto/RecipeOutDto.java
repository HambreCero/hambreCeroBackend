package com.example.recipeapp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class RecipeOutDto {
    private Long id;
    private String name;
    private int difficulty;
    private boolean vegetarian;
    private BigDecimal estimatedCost;
    private LocalDate lastModified;
    private int servings;

    public List<Long> ingredientIds;
    private String imageUrl;

}
