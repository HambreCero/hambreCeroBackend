package com.example.recipeapp.domain;

// import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name= "Ingredient")
@Table(name= "ingredients")
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "Ingredient name cannot be null")
    private String name;

    @Column
    @Min(value = 0, message = "Calories must be a positive number")
    private int calories;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Season season; // spring, summer, outumn, winter
   
    @Column
    private boolean isOrganic;

    @Column
    private LocalDate harvestDate;

    @Column(name = "price_kg")
    @Min(value = 0, message = "Price per kg must be positive")
    private BigDecimal priceKg;

    @Column(name = "carbon_footprint")
    private BigDecimal carbonFootprint;
}

