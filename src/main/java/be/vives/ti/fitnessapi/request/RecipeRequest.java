package be.vives.ti.fitnessapi.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

//Request type of the recipe model. It is used to save recipes.
public class RecipeRequest {
    @NotNull
    @NotEmpty
    private String recipeName;

    @NotNull
    @NotEmpty
    @Valid
    @Field
    private List<RecipeInstructionRequest> recipeInstructions;

    @NotNull
    @NotEmpty
    @Valid
    @Field
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
