package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.repository.RecipeRepository;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeResponse> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(RecipeResponse::new).collect(Collectors.toList());
    }

    public ResponseEntity<RecipeResponse> findByRecipeName(String recipeName) {
        Optional<Recipe> recipe = recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName);
        if(recipe.isPresent()) {
            RecipeResponse response = new RecipeResponse(recipe.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    public List<RecipeResponse> findByCaloriesBetween(double startCalorie, double endCalorie) {
        List<Recipe> recipes = recipeRepository.findByTotalKiloCaloriesBetween(startCalorie, endCalorie);
        return recipes.stream().map(RecipeResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<Void> deleteById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if(recipe.isPresent()) {
            recipeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    public RecipeResponse saveRecipe(Recipe savedRecipe) {
        Recipe recipe = recipeRepository.save(savedRecipe);
        return new RecipeResponse(recipe);
    }

    public ResponseEntity<String> updateRecipe(Long id, Recipe updatedRecipe) {
        Optional<Recipe> foundRecipe = recipeRepository.findById(id);

        if(foundRecipe.isPresent()) {
            Recipe recipe = foundRecipe.get();
            recipe.setRecipeName(updatedRecipe.getRecipeName());
            recipe.setRecipeIngredients(updatedRecipe.getRecipeIngredients());
            recipe.setRecipeInstruction(updatedRecipe.getRecipeInstructions());
            recipe.setTotalKiloCalories(updatedRecipe.getTotalKiloCalories());

            recipeRepository.save(updatedRecipe);
            return ResponseEntity.ok("Recipe succesfully updated.");
        }

        return ResponseEntity.notFound().build();
    }
}
