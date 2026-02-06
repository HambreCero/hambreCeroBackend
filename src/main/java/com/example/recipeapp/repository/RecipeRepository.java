package com.example.recipeapp.repository;

import com.example.recipeapp.domain.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @EntityGraph(attributePaths = "ingredients")
    Optional<Recipe> findById(Long id);
}




