package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.response.RecipeResponse;
import be.vives.ti.fitnessapi.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/recipes", produces = "application/json")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<RecipeResponse> findAll() {
        return recipeService.findAll();
    }

    @GetMapping("name/{name}")
    public ResponseEntity<RecipeResponse> findByRecipeName(@PathVariable("name") String recipeName) {
        return recipeService.findByRecipeName(recipeName);
    }

    @GetMapping("calories")
    public List<RecipeResponse> findByCaloriesBetween(
            @RequestParam("startcalories") double startCalories,
            @RequestParam("endcalories") double endCalories) {
        return recipeService.findByCaloriesBetween(startCalories, endCalories);
    }
}
