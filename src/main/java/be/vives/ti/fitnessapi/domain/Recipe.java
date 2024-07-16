package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "RECIPE")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String recipeName;

    @NotNull
    private String recipeInstruction;

    @NotNull
    private double totalCalories;


    public Long getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeInstruction() {
        return recipeInstruction;
    }

    public double getCalories() {
        return totalCalories;
    }
}
