package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.RecipeIngredient;
//import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class RecipeIngredientResponse {

    private String ingredientName;

    private String ingredientAmount;

    public RecipeIngredientResponse(RecipeIngredient recipeIngredient) {
        this.ingredientName = recipeIngredient.getIngredientName();
        this.ingredientAmount = recipeIngredient.getIngredientAmount();
    }


    public String getIngredientName() {
        return ingredientName;
    }


    public String getIngredientAmount() {
        return ingredientAmount;
    }

}
