package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.RecipeIngredient;

//This model is used in the RecipeResponse model.
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
