package be.vives.ti.fitnessapi.domain;

//import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("recipes")
public class Recipe {
    @Id
    private String id;

    @NotNull
    @NotEmpty
    @Field("recipeName")
    private String recipeName;

    //Each recipe has a list of instructions
    @NotNull
    @NotEmpty
    @Valid
    @Field("recipeInstructions")
    private List<RecipeInstruction> recipeInstructions;

    //Each recipe has a list of ingredients
    @NotNull
    @NotEmpty
    @Valid
    @Field("recipeIngredients")
    private List<RecipeIngredient> recipeIngredients;

    //Amount of kiloCalories consumed
    @NotNull
    @Min(0)
    @Field("totalKiloCalories")
    private double totalKiloCalories;

    public Recipe(String recipeName, List<RecipeInstruction> recipeInstructions, List<RecipeIngredient> recipeIngredients, double totalCalories) {
        this.recipeName = recipeName;
        this.recipeInstructions = recipeInstructions;
        this.recipeIngredients = recipeIngredients;
        this.totalKiloCalories = totalCalories;
    }

    protected Recipe() {
    }

    public String getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public List<RecipeInstruction> getRecipeInstructions() {
        return recipeInstructions;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public double getTotalKiloCalories() {
        return totalKiloCalories;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeInstructions(List<RecipeInstruction> recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public void addRecipeInstruction(RecipeInstruction instruction) {
        recipeInstructions.add(instruction);
    }

    public void addRecipeIngredient(RecipeIngredient ingredient) {
        recipeIngredients.add(ingredient);
    }

    public void setTotalKiloCalories(double totalKiloCalories) {
        this.totalKiloCalories = totalKiloCalories;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", recipeName='" + recipeName + '\'' +
                ", recipeInstruction='" + recipeInstructions + '\'' +
                ", totalCalories=" + totalKiloCalories +
                '}';
    }
}
