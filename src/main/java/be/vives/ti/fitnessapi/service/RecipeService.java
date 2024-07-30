package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.domain.RecipeIngredient;
import be.vives.ti.fitnessapi.domain.RecipeInstruction;
import be.vives.ti.fitnessapi.repository.RecipeRepository;
import be.vives.ti.fitnessapi.request.RecipeIngredientRequest;
import be.vives.ti.fitnessapi.request.RecipeInstructionRequest;
import be.vives.ti.fitnessapi.request.RecipeRequest;
import be.vives.ti.fitnessapi.response.RecipeResponse;
//import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.ArrayList;
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


    public ResponseEntity<RecipeResponse> findById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()) {
            RecipeResponse response = new RecipeResponse(recipe.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<RecipeResponse> findByRecipeName(String recipeName) {
        Optional<Recipe> recipe = recipeRepository.findByRecipeNameIgnoreCase(recipeName);
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
    public ResponseEntity<String> deleteById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if(recipe.isPresent()) {
            recipeRepository.deleteById(id);
            return ResponseEntity.ok("Recipe '" + recipe.get().getRecipeName() + "' succesfully deleted.");
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<Object> saveRecipe(RecipeRequest savedRecipe) {
        if(recipeRepository.existsByRecipeName(savedRecipe.getRecipeName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Recipe " + savedRecipe.getRecipeName() + " already exists.");
        }

        List<RecipeIngredient> recipeIngredients = savedRecipe.getRecipeIngredients().stream()
                .map(req -> new RecipeIngredient(req.getIngredientName(), req.getIngredientAmount()))
                .toList();

        List<RecipeInstruction> recipeInstructions = savedRecipe.getRecipeInstructions().stream()
                .map(req -> new RecipeInstruction(req.getInstruction(), req.getStep()))
                .toList();


        Recipe recipe = recipeRepository.save(new Recipe(savedRecipe.getRecipeName(),
                recipeInstructions,
                recipeIngredients,
                savedRecipe.getTotalKiloCalories()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
              .buildAndExpand(recipe.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<String> updateRecipe(String id, RecipeRequest updatedRecipe) {
        Optional<Recipe> foundRecipe = recipeRepository.findById(id);

        if (foundRecipe.isPresent()) {
            Recipe recipe = foundRecipe.get();

            recipe.setRecipeName(updatedRecipe.getRecipeName());
            recipe.setTotalKiloCalories(updatedRecipe.getTotalKiloCalories());

            recipe.getRecipeInstructions().clear();
            recipe.getRecipeIngredients().clear();

            for (RecipeInstructionRequest instructionRequest : updatedRecipe.getRecipeInstructions()) {
                RecipeInstruction instruction = new RecipeInstruction(instructionRequest.getInstruction(), instructionRequest.getStep());
                recipe.addRecipeInstruction(instruction);
            }

            for (RecipeIngredientRequest ingredientRequest : updatedRecipe.getRecipeIngredients()) {
                RecipeIngredient ingredient = new RecipeIngredient(ingredientRequest.getIngredientName(), ingredientRequest.getIngredientAmount());
                recipe.addRecipeIngredient(ingredient);
            }

            recipeRepository.save(recipe);
            return ResponseEntity.ok("Recipe successfully updated.");
        }

        return ResponseEntity.notFound().build();
    }
}
