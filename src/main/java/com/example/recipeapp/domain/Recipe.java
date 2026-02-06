package com.example.recipeapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Recipe")
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "Recipe name cannot be null")
    private String name;

    @Column
    private int difficulty; // 1–5

    @Column(name = "is_vegetarian")
    private boolean vegetarian;

    @Column(name = "estimated_cost")
    @Min(value = 0, message = "Cost must be positive")
    private BigDecimal estimatedCost;

    @Column(name = "last_modified")
    private LocalDate lastModified;

    @Column
    private int servings;

    @ManyToMany
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;
}