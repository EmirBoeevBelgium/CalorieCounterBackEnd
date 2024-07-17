package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class RecipeIngredient {
    @NotNull
    private String ingredientName;

    @NotNull
    @Column(length = 10)
    private String ingredientAmount;

    public RecipeIngredient(String ingredientName, String ingredientAmount) {
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
    }

    protected RecipeIngredient() {

    }

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
