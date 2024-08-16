package be.vives.ti.fitnessapi.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//Request type of the ingredient model
public class RecipeIngredientRequest {
    @NotNull
    @NotEmpty
    private String ingredientName;

    @NotNull
    @NotEmpty
    private String ingredientAmount;

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(String ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }
}
