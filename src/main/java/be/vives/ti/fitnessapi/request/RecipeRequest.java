package be.vives.ti.fitnessapi.request;

import be.vives.ti.fitnessapi.domain.RecipeIngredient;
import be.vives.ti.fitnessapi.domain.RecipeInstruction;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RecipeRequest {
    @NotNull
    @NotEmpty
    private String recipeName;

    @NotNull
    @NotEmpty
    @Valid
    @ElementCollection
    @CollectionTable(name = "RECIPE_INSTRUCTIONS", joinColumns = @JoinColumn( name = "recipe_id"))
    private List<RecipeInstructionRequest> recipeInstructions;

    @NotNull
    @NotEmpty
    @Valid
    @ElementCollection
    @CollectionTable(name = "RECIPE_INGREDIENTS", joinColumns = @JoinColumn( name = "recipe_id"))
    private List<RecipeIngredientRequest> recipeIngredients;

    @NotNull
    @Min(0)
    private double totalKiloCalories;


    public String getRecipeName() {
        return recipeName;
    }

    public List<RecipeInstructionRequest> getRecipeInstructions() {
        return recipeInstructions;
    }

    public List<RecipeIngredientRequest> getRecipeIngredients() {
        return recipeIngredients;
    }

    public double getTotalKiloCalories() {
        return totalKiloCalories;
    }


    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeInstructions(List<RecipeInstructionRequest> recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public void setRecipeIngredients(List<RecipeIngredientRequest> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public void setTotalKiloCalories(double totalKiloCalories) {
        this.totalKiloCalories = totalKiloCalories;
    }
}
