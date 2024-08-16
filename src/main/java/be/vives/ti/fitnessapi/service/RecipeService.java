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

    //Get all recipes
    public List<RecipeResponse> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(RecipeResponse::new).collect(Collectors.toList());
    }

    //Get recipe by ID
    public ResponseEntity<RecipeResponse> findById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()) {
            RecipeResponse response = new RecipeResponse(recipe.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    //Get exact recipe by name
    public ResponseEntity<RecipeResponse> findByRecipeName(String recipeName) {
        Optional<Recipe> recipe = recipeRepository.findByRecipeNameIgnoreCase(recipeName);
        //Check if recipe exists
        if(recipe.isPresent()) {
            //Convert to RecipeRespone
            RecipeResponse response = new RecipeResponse(recipe.get());
            //Return 200 with response
            return ResponseEntity.ok(response);
        }
        //Return 404 if not found
        return ResponseEntity.notFound().build();
    }

    //Find recipes between set of kiloCalories
    public List<RecipeResponse> findByCaloriesBetween(double startCalorie, double endCalorie) {
        List<Recipe> recipes = recipeRepository.findByTotalKiloCaloriesBetween(startCalorie, endCalorie);
        return recipes.stream().map(RecipeResponse::new).collect(Collectors.toList());
    }

    //Delete recipe by ID
    @Transactional
    public ResponseEntity<String> deleteById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if(recipe.isPresent()) {
            recipeRepository.deleteById(id);
            return ResponseEntity.ok("Recipe '" + recipe.get().getRecipeName() + "' succesfully deleted.");
        }
        return ResponseEntity.notFound().build();

    }

    //Save new recipe
    public ResponseEntity<Object> saveRecipe(RecipeRequest savedRecipe) {
        //Check if recipe exists by name
        if(recipeRepository.existsByRecipeName(savedRecipe.getRecipeName())) {
            //Return 409 if recipe already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Recipe " + savedRecipe.getRecipeName() + " already exists.");
        }

        //Map the ingredients to a list of RecipeIngredients
        List<RecipeIngredient> recipeIngredients = savedRecipe.getRecipeIngredients().stream()
                .map(req -> new RecipeIngredient(req.getIngredientName(), req.getIngredientAmount()))
                .toList();
        //Map the instructions to a list of RecipeInstructions
        List<RecipeInstruction> recipeInstructions = savedRecipe.getRecipeInstructions().stream()
                .map(req -> new RecipeInstruction(req.getInstruction(), req.getStep()))
                .toList();

        //Save the recipe
        Recipe recipe = recipeRepository.save(new Recipe(savedRecipe.getRecipeName(),
                recipeInstructions,
                recipeIngredients,
                savedRecipe.getTotalKiloCalories()));
        //Get the URI location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
              .buildAndExpand(recipe.getId()).toUri();

        //Return 201 with the location
        return ResponseEntity.created(location).build();
    }


    //Update the recipe
    public ResponseEntity<String> updateRecipe(String id, RecipeRequest updatedRecipe) {
        Optional<Recipe> foundRecipe = recipeRepository.findById(id);

        //Check if recipe exists
        if (foundRecipe.isPresent()) {
            Recipe recipe = foundRecipe.get();

            //Set recipe name
            recipe.setRecipeName(updatedRecipe.getRecipeName());
            //Set consumale kiloCalories
            recipe.setTotalKiloCalories(updatedRecipe.getTotalKiloCalories());

            //Clear both instruction and ingredients
            recipe.getRecipeInstructions().clear();
            recipe.getRecipeIngredients().clear();

            //Apply new instructions
            for (RecipeInstructionRequest instructionRequest : updatedRecipe.getRecipeInstructions()) {
                RecipeInstruction instruction = new RecipeInstruction(instructionRequest.getInstruction(), instructionRequest.getStep());
                recipe.addRecipeInstruction(instruction);
            }

            //Apply new ingredients
            for (RecipeIngredientRequest ingredientRequest : updatedRecipe.getRecipeIngredients()) {
                RecipeIngredient ingredient = new RecipeIngredient(ingredientRequest.getIngredientName(), ingredientRequest.getIngredientAmount());
                recipe.addRecipeIngredient(ingredient);
            }

            //Save the recipe and return 202
            recipeRepository.save(recipe);
            return ResponseEntity.ok("Recipe successfully updated.");
        }

        //If the recipe is not found return 404
        return ResponseEntity.notFound().build();
    }
}
