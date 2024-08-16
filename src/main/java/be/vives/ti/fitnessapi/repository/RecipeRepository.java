package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.domain.Workout;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    //Find exact recipe by name, ignoring the case
    Optional<Recipe> findByRecipeNameIgnoreCase(String recipeName);
    //Find recipes that are between a specified set of kiloCalories
    List<Recipe> findByTotalKiloCaloriesBetween(double startCalories, double endCalories);
    //Check if the recipe exists by name
    boolean existsByRecipeName(String recipeName);

    //Delete recipe by name
    void deleteRecipeById(String id);
}
