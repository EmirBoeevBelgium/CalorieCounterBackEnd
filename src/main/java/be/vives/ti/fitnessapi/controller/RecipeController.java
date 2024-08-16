package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.request.RecipeRequest;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import be.vives.ti.fitnessapi.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping( value = "recipes", produces = "application/json")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //GET ALL RECIPES
    @GetMapping
    public List<RecipeResponse> findAll() {
        return recipeService.findAll();
    }

    //GET EXACT RECIPE BY NAME
    @GetMapping("recipe")
    public ResponseEntity<RecipeResponse> findByRecipeName(@RequestParam("name") String recipeName) {
        return recipeService.findByRecipeName(recipeName);
    }

    //GET LIST OF RECIPES THAT ARE BETWEEN A SPECIFIED AMOUNT OF KILOCALORIES
    @GetMapping("calories")
    public List<RecipeResponse> findByCaloriesBetween(
            @RequestParam("startcalories") double startCalories,
            @RequestParam("endcalories") double endCalories) {
        return recipeService.findByCaloriesBetween(startCalories, endCalories);
    }

    //DELETE RECIPE BY ID
    @DeleteMapping("recipe")
    public ResponseEntity<String> deleteById(@RequestParam("id") String id) {
        return recipeService.deleteById(id);
    }

    //SAVE NEW RECIPE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveRecipe(@Valid @RequestBody RecipeRequest recipe) {
        return recipeService.saveRecipe(recipe);
    }

    //UPDATE RECIPE
    @PutMapping("recipe")
    public ResponseEntity<String> updateRecipe(@RequestParam("id") String id, @Valid @RequestBody RecipeRequest updatedRecipe) {
        return recipeService.updateRecipe(id, updatedRecipe);
    }

}
