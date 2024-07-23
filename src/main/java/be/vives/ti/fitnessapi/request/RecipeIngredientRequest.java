package be.vives.ti.fitnessapi.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RecipeIngredientRequest {
    @NotNull
    @NotEmpty
    private String ingredientName;

    @NotNull
    @NotEmpty
    @Column(length = 10)
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
