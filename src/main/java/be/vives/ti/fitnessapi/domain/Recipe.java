package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "RECIPE")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String recipeName;

    @NotNull
    @Column(length = 5000)
    private String recipeInstruction;

    @NotNull
    private double totalCalories;


    public Long getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeInstruction() {
        return recipeInstruction;
    }

    public double getCalories() {
        return totalCalories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeInstruction(String recipeInstruction) {
        this.recipeInstruction = recipeInstruction;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", recipeName='" + recipeName + '\'' +
                ", recipeInstruction='" + recipeInstruction + '\'' +
                ", totalCalories=" + totalCalories +
                '}';
    }
}
