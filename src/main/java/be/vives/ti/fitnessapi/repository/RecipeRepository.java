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
    Optional<Recipe> findByRecipeNameIgnoreCase(String recipeName);
    List<Recipe> findByTotalKiloCaloriesBetween(double startCalories, double endCalories);
    boolean existsByRecipeName(String recipeName);
    void deleteRecipeById(String id);
}
