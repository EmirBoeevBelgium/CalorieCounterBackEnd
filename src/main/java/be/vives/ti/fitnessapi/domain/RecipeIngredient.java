package be.vives.ti.fitnessapi.domain;

//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;

//@Embeddable
public class RecipeIngredient {
    @NotNull
    @NotEmpty
    @Field("ingredientName")
    private String ingredientName;

    @NotNull
    @NotEmpty
    @Field("ingredientAmount")
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
    /*
    @NotNull
    @NotEmpty
    private String ingredientName;

    @NotNull
    @NotEmpty
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
    }*/
}
