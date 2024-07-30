package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.*;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() throws Exception {
        recipeRepository.deleteAll();
    }
    @Test
    public void simpleCrud() {
        List<RecipeIngredient> baconIngredients = new ArrayList<>();

        RecipeIngredient bacon = new RecipeIngredient("Bacon", "3-4 strips");
        RecipeIngredient fryingOil = new RecipeIngredient("Frying oil", "3 ml");

        baconIngredients.add(bacon);
        baconIngredients.add(fryingOil);

        List<RecipeInstruction> baconInstructions = new ArrayList<>();

        RecipeInstruction baconStep1 = new RecipeInstruction("Preheat the pan on high temperature for 5 minutes.", 1);
        RecipeInstruction baconStep2 = new RecipeInstruction("Put oil on the pan.", 2);
        RecipeInstruction baconStep3 = new RecipeInstruction("Put the bacon strips on the pan.",3);
        RecipeInstruction baconStep4 = new RecipeInstruction("Fry for 3-4 minutes.",3);
        RecipeInstruction baconStep5 = new RecipeInstruction("Serve with salt",4);

        baconInstructions.add(baconStep1);
        baconInstructions.add(baconStep2);
        baconInstructions.add(baconStep3);
        baconInstructions.add(baconStep4);
        baconInstructions.add(baconStep5);

        Recipe baconRecipe = new Recipe("Bacon", baconInstructions, baconIngredients, 400);
        baconRecipe = recipeRepository.save(baconRecipe);

        assertThat(baconRecipe.getId()).isNotNull();
        assertThat(recipeRepository.findAll().size()).isEqualTo(1);
        assertThat(baconRecipe.getRecipeName()).isEqualTo("Bacon");
        assertThat(baconRecipe.getTotalKiloCalories()).isEqualTo(400);
        assertThat(baconRecipe.getRecipeInstructions().size()).isEqualTo(5);
        assertThat(baconRecipe.getRecipeIngredients().size()).isEqualTo(2);

        baconRecipe.setRecipeName("Fried bacon");
        baconRecipe.setTotalKiloCalories(500);
        baconRecipe.addRecipeInstruction(new RecipeInstruction("Eat", 6));
        baconRecipe.addRecipeIngredient(new RecipeIngredient("Salt", "200g"));

        baconRecipe = recipeRepository.save(baconRecipe);

        assertThat(baconRecipe.getRecipeName()).isEqualTo("Fried bacon");
        assertThat(baconRecipe.getTotalKiloCalories()).isEqualTo(500);
        assertThat(baconRecipe.getRecipeInstructions().size()).isEqualTo(6);
        assertThat(baconRecipe.getRecipeIngredients().size()).isEqualTo(3);



        List<RecipeInstruction> macAndCheeseInstructions = new ArrayList<>();
        RecipeInstruction macAndCheeseStep1 = new RecipeInstruction("Preheat the pan high temperature.", 1);
        RecipeInstruction macAndCheeseStep2 = new RecipeInstruction("Add the water halfway to the pan.", 2);
        RecipeInstruction macAndCheeseStep3 = new RecipeInstruction("Add the water halfway to the pan.", 3);
        RecipeInstruction macAndCheeseStep4 = new RecipeInstruction("Boil for 15 minutes.", 4);
        RecipeInstruction macAndCheeseStep5 = new RecipeInstruction("Drain the water.", 5);
        RecipeInstruction macAndCheeseStep6 = new RecipeInstruction("Add the cheddar cheese.", 6);
        RecipeInstruction macAndCheeseStep7 = new RecipeInstruction("Wait 5 minutes for the cheese to melt.", 7);
        RecipeInstruction macAndCheeseStep8 = new RecipeInstruction("Serve with ketchup.", 8);

        macAndCheeseInstructions.add(macAndCheeseStep1);
        macAndCheeseInstructions.add(macAndCheeseStep2);
        macAndCheeseInstructions.add(macAndCheeseStep3);
        macAndCheeseInstructions.add(macAndCheeseStep4);
        macAndCheeseInstructions.add(macAndCheeseStep5);
        macAndCheeseInstructions.add(macAndCheeseStep6);
        macAndCheeseInstructions.add(macAndCheeseStep7);
        macAndCheeseInstructions.add(macAndCheeseStep8);

        List<RecipeIngredient> macAndCheeseIngredients = new ArrayList<>();

        RecipeIngredient macaroni = new RecipeIngredient("Macaroni", "1 serving");
        RecipeIngredient cheddarCheese = new RecipeIngredient("Cheddar cheese", "100g");

        macAndCheeseIngredients.add(macaroni);
        macAndCheeseIngredients.add(cheddarCheese);

        Recipe macAndCheese = new Recipe("Mac & cheese", macAndCheeseInstructions, macAndCheeseIngredients, 345);
        Workout dumbellCurls = new Workout("Dumbell curls", 200);
        macAndCheese = recipeRepository.save(macAndCheese);
        assertThat(macAndCheese.getId()).isNotNull();
        assertThat(recipeRepository.findAll().size()).isEqualTo(2);

        Recipe recipeWithId = recipeRepository.findById(macAndCheese.getId()).get();
        assertThat(recipeWithId.getId()).isEqualTo(macAndCheese.getId());
        assertThat(recipeWithId.getRecipeName()).isEqualTo(macAndCheese.getRecipeName());

        recipeRepository.delete(recipeWithId);
        assertThat(recipeRepository.findAll().size()).isEqualTo(1);
        assertThat(recipeRepository.findById(macAndCheese.getId()).isEmpty()).isTrue();
    }

    @Test
    void findByRecipeNameIgnoreCase() {
        List<RecipeInstruction> recipeInstructionsTest = new ArrayList<>();
        recipeInstructionsTest.add(new RecipeInstruction("test instruction 1", 1));
        List<RecipeIngredient> recipeIngredientsTest = new ArrayList<>();
        recipeIngredientsTest.add(new RecipeIngredient("test ingredient 1", "1 piece"));

        recipeRepository.save(new Recipe("Bacon", recipeInstructionsTest, recipeIngredientsTest, 200));
        recipeRepository.save(new Recipe("Mac & cheese", recipeInstructionsTest, recipeIngredientsTest, 300));
        recipeRepository.save(new Recipe("Lasagna", recipeInstructionsTest, recipeIngredientsTest, 400));

        assertThat(recipeRepository.findAll().size()).isEqualTo(3);

        Recipe backend = recipeRepository.findByRecipeNameIgnoreCase("lasagna").get();
        assertThat(backend.getRecipeName()).isEqualTo("Lasagna");

        Recipe bacon = recipeRepository.findByRecipeNameIgnoreCase("bacon").get();
        assertThat(bacon.getRecipeName()).isEqualTo("Bacon");

        assertThat(recipeRepository.findByRecipeNameIgnoreCase("zbvzibvzvb").isEmpty()).isTrue();
    }

    @Test
    void findByKiloCaloriesBetween() {
        List<RecipeInstruction> recipeInstructionsTest = new ArrayList<>();
        recipeInstructionsTest.add(new RecipeInstruction("test instruction 1", 1));
        List<RecipeIngredient> recipeIngredientsTest = new ArrayList<>();
        recipeIngredientsTest.add(new RecipeIngredient("test ingredient 1", "1 piece"));

        recipeRepository.save(new Recipe("Bacon", recipeInstructionsTest, recipeIngredientsTest, 200));
        recipeRepository.save(new Recipe("Mac & cheese", recipeInstructionsTest, recipeIngredientsTest, 300));
        recipeRepository.save(new Recipe("Lasagna", recipeInstructionsTest, recipeIngredientsTest, 400));

        assertThat(recipeRepository.findAll().size()).isEqualTo(3);

        List<Recipe> recipeResponses = recipeRepository.findByTotalKiloCaloriesBetween(250, 450);

        assertThat(recipeResponses.size()).isEqualTo(2);
        assertThat(recipeResponses.get(0).getRecipeName()).isEqualTo("Mac & cheese");
        assertThat(recipeResponses.get(1).getRecipeName()).isEqualTo("Lasagna");

        recipeResponses = recipeRepository.findByTotalKiloCaloriesBetween(1000, 2000);

        assertThat(recipeResponses.size()).isEqualTo(0);

        recipeResponses = recipeRepository.findByTotalKiloCaloriesBetween(1000, 500);

        assertThat(recipeResponses.size()).isEqualTo(0);

    }

    @Test
    void existsByRecipeName() {
        List<RecipeInstruction> recipeInstructionsTest = new ArrayList<>();
        recipeInstructionsTest.add(new RecipeInstruction("test instruction 1", 1));
        List<RecipeIngredient> recipeIngredientsTest = new ArrayList<>();
        recipeIngredientsTest.add(new RecipeIngredient("test ingredient 1", "1 piece"));

        recipeRepository.save(new Recipe("Bacon", recipeInstructionsTest, recipeIngredientsTest, 200));
        recipeRepository.save(new Recipe("Mac & cheese", recipeInstructionsTest, recipeIngredientsTest, 300));
        recipeRepository.save(new Recipe("Lasagna", recipeInstructionsTest, recipeIngredientsTest, 400));

        assertThat(recipeRepository.existsByRecipeName("Bacon")).isTrue();
        assertThat(recipeRepository.existsByRecipeName("Mac & cheese")).isTrue();
        assertThat(recipeRepository.existsByRecipeName("Lasagna")).isTrue();

    }

    @Test
    void deleteByRecipeId() {
        List<RecipeInstruction> recipeInstructionsTest = new ArrayList<>();
        recipeInstructionsTest.add(new RecipeInstruction("test instruction 1", 1));
        List<RecipeIngredient> recipeIngredientsTest = new ArrayList<>();
        recipeIngredientsTest.add(new RecipeIngredient("test ingredient 1", "1 piece"));

        Recipe toBeDeleted = recipeRepository.save(new Recipe("Bacon", recipeInstructionsTest, recipeIngredientsTest, 200));
        recipeRepository.save(new Recipe("Mac & cheese", recipeInstructionsTest, recipeIngredientsTest, 300));
        recipeRepository.save(new Recipe("Lasagna", recipeInstructionsTest, recipeIngredientsTest, 400));

        assertThat(recipeRepository.findAll().size()).isEqualTo(3);
        assertThat(recipeRepository.findAll().get(0).getRecipeName()).isEqualTo("Bacon");
        assertThat(recipeRepository.findAll().get(1).getRecipeName()).isEqualTo("Mac & cheese");
        assertThat(recipeRepository.findAll().get(2).getRecipeName()).isEqualTo("Lasagna");

        System.out.println(recipeRepository.findAll());

        recipeRepository.deleteById(toBeDeleted.getId());

        assertThat(recipeRepository.findAll().size()).isEqualTo(2);
        assertThat(recipeRepository.findAll().get(0).getRecipeName()).isEqualTo("Mac & cheese");
        assertThat(recipeRepository.findAll().get(1).getRecipeName()).isEqualTo("Lasagna");

    }

}