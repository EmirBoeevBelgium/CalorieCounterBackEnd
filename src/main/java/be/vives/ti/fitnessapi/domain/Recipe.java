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

//@Entity
//@Table(name = "RECIPE")
@Document("recipes")
public class Recipe {
    @Id
    private String id;

    @NotNull
    @NotEmpty
    @Field("recipeName")
    private String recipeName;

    @NotNull
    @NotEmpty
    @Valid
    @Field("recipeInstructions")
    private List<RecipeInstruction> recipeInstructions;

    @NotNull
    @NotEmpty
    @Valid
    @Field("recipeIngredients")
    private List<RecipeIngredient> recipeIngredients;

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
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Field
    private String recipeName;

    @NotNull
    @NotEmpty
    @Valid
    @ElementCollection
    @CollectionTable(name = "RECIPE_INSTRUCTIONS", joinColumns = @JoinColumn( name = "recipe_id"))
    private List<RecipeInstruction> recipeInstructions;

    @NotNull
    @NotEmpty
    @Valid
    @ElementCollection
    @CollectionTable(name = "RECIPE_INGREDIENTS", joinColumns = @JoinColumn( name = "recipe_id"))
    private List<RecipeIngredient> recipeIngredients;

    @NotNull
    @Min(0)
    private double totalKiloCalories;


    public Recipe(String recipeName, List<RecipeInstruction> recipeInstructions, List<RecipeIngredient> recipeIngredients, double totalCalories) {

        this.recipeName = recipeName;
        this.recipeInstructions = recipeInstructions;
        this.recipeIngredients = recipeIngredients;
        this.totalKiloCalories = totalCalories;
    }

    protected Recipe() {

    }

    public Long getId() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeInstruction(List<RecipeInstruction> recipeInstructions) {
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
    }*/
}
