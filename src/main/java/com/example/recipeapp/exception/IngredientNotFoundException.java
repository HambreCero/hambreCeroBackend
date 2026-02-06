package com.example.recipeapp.exception;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(Long id) {
        super("Ingredient not found: " + id);
    }
}
