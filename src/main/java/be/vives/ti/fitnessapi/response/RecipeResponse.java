package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeResponse {
    private Long id;

    private String recipeName;

    private double totalKiloCalories;

    private List<RecipeInstructionResponse> recipeInstructions;
    private List<RecipeIngredientResponse> recipeIngredients;

    public RecipeResponse(Recipe recipe) {
        this.id = recipe.getId();
        this.recipeName = recipe.getRecipeName();
        this.totalKiloCalories = recipe.getTotalKiloCalories();
        this.recipeInstructions = recipe.getRecipeInstructions().stream().map(RecipeInstructionResponse::new).collect(Collectors.toList());
        this.recipeIngredients = recipe.getRecipeIngredients().stream().map(RecipeIngredientResponse::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public double getTotalKiloCalories() {
        return totalKiloCalories;
    }

    public List<RecipeInstructionResponse> getRecipeInstructions() {
        return recipeInstructions;
    }
    public List<RecipeIngredientResponse> getRecipeIngredients() {
        return recipeIngredients;
    }
}
