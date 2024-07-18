package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName);
    List<Recipe> findByTotalKiloCaloriesBetween(double startCalories, double endCalories);
    void deleteRecipeById(Long id);
}
