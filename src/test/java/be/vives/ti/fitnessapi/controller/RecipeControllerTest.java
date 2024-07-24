package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.domain.*;
import be.vives.ti.fitnessapi.repository.RecipeRepository;
import be.vives.ti.fitnessapi.request.RecipeIngredientRequest;
import be.vives.ti.fitnessapi.request.RecipeInstructionRequest;
import be.vives.ti.fitnessapi.request.RecipeRequest;
import be.vives.ti.fitnessapi.response.MuscleGroupResponse;
import be.vives.ti.fitnessapi.response.RecipeIngredientResponse;
import be.vives.ti.fitnessapi.response.RecipeInstructionResponse;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import be.vives.ti.fitnessapi.service.MuscleGroupService;
import be.vives.ti.fitnessapi.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    private final String apiUrl = "/recipes";

    @MockBean
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Recipe> recipes;

    @BeforeEach
    void setUp() {
        recipes = new ArrayList<>();
        List<RecipeInstruction> chickenAndRiceInstr = new ArrayList<>();
        List<RecipeIngredient> chickenAndRiceIngr = new ArrayList<>();

        List<RecipeInstruction> bologneseInstr = new ArrayList<>();
        List<RecipeIngredient> bologneseIngr = new ArrayList<>();

        List<RecipeInstruction> friedEggInstr = new ArrayList<>();
        List<RecipeIngredient> friedEggIngr = new ArrayList<>();

        friedEggInstr.add(new RecipeInstruction("Cover frying pan with oil.", 1));
        friedEggInstr.add(new RecipeInstruction("Set stove to medium-high heat.", 2));
        friedEggInstr.add(new RecipeInstruction("Wait for 5 minutes or until it starts sizzling.", 3));
        friedEggInstr.add(new RecipeInstruction("Crack an egg open and drop it straight into pan.", 4));
        friedEggInstr.add(new RecipeInstruction("Fry for 1 minute and you're done.", 5));

        friedEggIngr.add(new RecipeIngredient("egg", "1-3 pieces to your choice"));
        friedEggIngr.add(new RecipeIngredient("frying butter", "1/4"));


        bologneseInstr.add(new RecipeInstruction("Put a large saucepan on a medium heat and add 1 tbsp olive oil.", 1));
        bologneseInstr.add(new RecipeInstruction("Add 4 finely chopped bacon rashers and fry for 10 mins until golden and crisp.", 2));
        bologneseInstr.add(new RecipeInstruction("Reduce the heat and add the 2 onions, 2 carrots, 2 celery sticks, 2 garlic cloves and the leaves from 2-3 sprigs rosemary, all finely chopped, then fry for 10 mins. Stir the veg often until it softens.", 3));
        bologneseInstr.add(new RecipeInstruction("Increase the heat to medium-high, add 500g beef mince and cook stirring for 3-4 mins until the meat is browned all over.", 4));
        bologneseInstr.add(new RecipeInstruction("Add 2 tins plum tomatoes, the finely chopped leaves from ¾ small pack basil, 1 tsp dried oregano, 2 bay leaves, 2 tbsp tomato purée, 1 beef stock cube, 1 deseeded and finely chopped red chilli (if using), 125ml red wine and 6 halved cherry tomatoes. Stir with a wooden spoon, breaking up the plum tomatoes.", 5));
        bologneseInstr.add(new RecipeInstruction("Bring to the boil, reduce to a gentle simmer and cover with a lid. Cook for 1 hr 15 mins stirring occasionally, until you have a rich, thick sauce.", 6));
        bologneseInstr.add(new RecipeInstruction("Add the 75g grated parmesan, check the seasoning and stir.", 7));
        bologneseInstr.add(new RecipeInstruction("When the bolognese is nearly finished, cook 400g spaghetti following the pack instructions.", 8));
        bologneseInstr.add(new RecipeInstruction("Drain the spaghetti and either stir into the bolognese sauce, or serve the sauce on top. Serve with more grated parmesan, the remaining basil leaves and crusty bread, if you like.", 9));


        bologneseIngr.add(new RecipeIngredient("olive oil", "1 tbspn"));
        bologneseIngr.add(new RecipeIngredient("smoked streaky bacon, finely chopped", "4 rashers"));
        bologneseIngr.add(new RecipeIngredient("medium onions, finely chopped", "2 pieces"));
        bologneseIngr.add(new RecipeIngredient("carrots, trimmed and finely chopped", "2 pieces"));
        bologneseIngr.add(new RecipeIngredient("celery sticks, finely chopped", "2 pieces"));
        bologneseIngr.add(new RecipeIngredient("garlic cloves finely chopped", "2 pieces"));
        bologneseIngr.add(new RecipeIngredient("rosemary leaves picked and finely chopped", "2-3 sprigs"));
        bologneseIngr.add(new RecipeIngredient("beef mince", "500g"));

        chickenAndRiceInstr.add(new RecipeInstruction("Cook rice for 30 minutes in 1/2 ounces of water.", 1));
        chickenAndRiceInstr.add(new RecipeInstruction("Cook chicken in other pot for 40 minutes in 1 liter of water.", 2));
        chickenAndRiceInstr.add(new RecipeInstruction("Drain water out of both and add chicken to rice", 3));

        chickenAndRiceIngr.add(new RecipeIngredient("Chicken","1/2 piece"));
        chickenAndRiceIngr.add(new RecipeIngredient("Rice","1 scoop"));

        recipes.add(new Recipe("Chicken and rice", chickenAndRiceInstr, chickenAndRiceIngr, 250.2));
        recipes.add(new Recipe("Bolognese", bologneseInstr, bologneseIngr, 625.4));
        recipes.add(new Recipe("Fried eggs", friedEggInstr, friedEggIngr, 194));


        when(recipeService.findAll()).thenReturn(recipes.stream().map(RecipeResponse::new).collect(Collectors.toList()));
    }


    @Test
    void findAllRecipes() throws Exception {
        when(recipeService.findAll()).thenReturn(recipes.stream().map(RecipeResponse::new).collect(Collectors.toList()));
        mockMvc.perform(get(apiUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].recipeName", equalTo("Chicken and rice")))
                .andExpect(jsonPath("$[1].recipeName", equalTo("Bolognese")))
                .andExpect(jsonPath("$[2].recipeName", equalTo("Fried eggs")));
    }

    @Test
    void findByRecipeNameUpperCase() throws Exception {
        RecipeResponse bolognese = new RecipeResponse(recipes.get(1));
        when(recipeService.findByRecipeName("BOLOGNESE")).thenReturn(ResponseEntity.ok(bolognese));
        mockMvc.perform(get(apiUrl + "/recipe").param("name", "BOLOGNESE"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName", equalTo("Bolognese")));
    }

    @Test
    void findByRecipeNameLowerCase() throws Exception {
        RecipeResponse bolognese = new RecipeResponse(recipes.get(1));
        when(recipeService.findByRecipeName("bolognese")).thenReturn(ResponseEntity.ok(bolognese));
        mockMvc.perform(get(apiUrl + "/recipe").param("name", "bolognese"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName", equalTo("Bolognese")));
    }

    @Test
    void findByRecipeNameRandomCase() throws Exception {
        RecipeResponse bolognese = new RecipeResponse(recipes.get(1));
        when(recipeService.findByRecipeName("bolOgNeSe")).thenReturn(ResponseEntity.ok(bolognese));
        mockMvc.perform(get(apiUrl + "/recipe").param("name", "bolOgNeSe"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName", equalTo("Bolognese")));
    }

    @Test
    void findByCaloriesBetween() throws Exception {
        List<RecipeResponse> foundRecipes = new ArrayList<>();
        foundRecipes.add(new RecipeResponse(recipes.get(0))); //Chicken and rice
        foundRecipes.add(new RecipeResponse(recipes.get(2))); //Fried egg(s)

        when(recipeService.findByCaloriesBetween(100, 300)).thenReturn(foundRecipes);
        mockMvc.perform(get(apiUrl + "/calories").param("startcalories", String.valueOf(100))
                        .param("endcalories", String.valueOf(300)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipeName", equalTo("Chicken and rice")))
                .andExpect(jsonPath("$[1].recipeName", equalTo("Fried eggs")));
    }

    @Test
    void deleteById() throws Exception {
        when(recipeService.deleteById(1L)).thenReturn(ResponseEntity.ok("Recipe 'Chicken and rice' succesfully deleted."));
        mockMvc.perform(delete(apiUrl + "/recipe").param("id", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk());

        when(recipeService.deleteById(1L)).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(delete(apiUrl + "/recipe").param("id", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteByIdNotFound() throws Exception {
        when(recipeService.deleteById(15L)).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(delete(apiUrl + "/recipe").param("id", String.valueOf(15L)))
                .andDo(print())
                .andExpect(status().isNotFound());


    }

    @Test
    void saveRecipe() throws Exception {
        List<RecipeInstruction> instructions = new ArrayList<>();
        RecipeInstruction testInstr1 = new RecipeInstruction("Instr 1", 1);
        RecipeInstruction testInstr2 = new RecipeInstruction("Instr 2", 1);


        List<RecipeIngredient> ingredients = new ArrayList<>();
        RecipeIngredient testIngr1 = new RecipeIngredient("Ingr 1", "1");
        RecipeIngredient testIngr2 = new RecipeIngredient("Ingr 2", "1");

        instructions.add(testInstr1);
        instructions.add(testInstr2);
        ingredients.add(testIngr1);
        ingredients.add(testIngr2);

        Recipe testRecipe = new Recipe("Test recipe", instructions, ingredients, 100);


        URI location = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8080/recipes?id=2").build().toUri();

        when(recipeService.saveRecipe(any(RecipeRequest.class))).thenReturn(ResponseEntity.created(location).build());
        mockMvc.perform(post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecipe)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("location", "http://localhost:8080/recipes?id=2"));

    }

    @Test
    void saveRecipeEmptyNameValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();
        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);
        invalidRecipe.setTotalKiloCalories(100);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeNegativeKiloCalValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();
        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);
        invalidRecipe.setTotalKiloCalories(-100);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }



    @Test
    void saveRecipeEmptyInstructionsValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();
        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);


        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeEmptyIngredientsValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeEmptyInstructionNameValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeEmptyIngredientNameValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeNullInstructionNameValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeNullIngredientNameValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();


        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeInstructionStepIsNullValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeEmptyIngredientAmountValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("test ingredient");
        testIngred.setIngredientAmount("");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeInstructionStepIsZeroValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("1 tbspn");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(0);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeIngredientAmountIsNullValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("test ingr");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeOneValidAndOneInvalidIngredientValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest validIngred = new RecipeIngredientRequest();
        RecipeIngredientRequest inValidIngred = new RecipeIngredientRequest();

        validIngred.setIngredientName("test ingr");
        validIngred.setIngredientAmount("2 tbspns");

        ingrList.add(validIngred);
        ingrList.add(inValidIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest testInstr = new RecipeInstructionRequest();
        testInstr.setInstruction("instruction 1");
        testInstr.setStep(1);
        instrList.add(testInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveRecipeOneValidAndOneInvalidInstructionValidationError() throws Exception {
        List<RecipeIngredientRequest> ingrList = new ArrayList<>();
        RecipeIngredientRequest testIngred = new RecipeIngredientRequest();

        testIngred.setIngredientName("test ingr");
        testIngred.setIngredientAmount("2 tbspns");
        ingrList.add(testIngred);

        List<RecipeInstructionRequest> instrList = new ArrayList<>();
        RecipeInstructionRequest validInstr = new RecipeInstructionRequest();
        RecipeInstructionRequest inValidInstr = new RecipeInstructionRequest();
        validInstr.setInstruction("instruction 1");
        validInstr.setStep(1);
        instrList.add(validInstr);
        instrList.add(inValidInstr);

        RecipeRequest invalidRecipe = new RecipeRequest();

        invalidRecipe.setRecipeName("test recipe");
        invalidRecipe.setRecipeInstructions(instrList);
        invalidRecipe.setRecipeIngredients(ingrList);

        mockMvc.perform(post(apiUrl).content(objectMapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateRecipe() throws Exception {
        Long recipeId = 3L;
        RecipeIngredient testIngr = new RecipeIngredient("test ingredient", "1 tblspn");
        RecipeInstruction testInstr = new RecipeInstruction("test ingredient", 1);

        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        List<RecipeInstruction> recipeInstructions = new ArrayList<>();

        recipeIngredients.add(testIngr);
        recipeInstructions.add(testInstr);

        Recipe testRecipe = new Recipe("Test recipe", recipeInstructions, recipeIngredients, 100);
        testRecipe.setId(recipeId);

        RecipeResponse recipeResponse = new RecipeResponse(testRecipe);

        when(recipeService.findById(recipeId)).thenReturn(ResponseEntity.ok(recipeResponse));


        List<RecipeIngredientRequest> recipeIngredientRequests = new ArrayList<>();
        List<RecipeInstructionRequest> recipeInstructionRequests = new ArrayList<>();

        RecipeIngredientRequest recipeIngredientRequestTest = new RecipeIngredientRequest();
        recipeIngredientRequestTest.setIngredientName("ingredient 1");
        recipeIngredientRequestTest.setIngredientAmount("2 tblspns");

        RecipeInstructionRequest recipeInstructionRequestTest = new RecipeInstructionRequest();
        recipeInstructionRequestTest.setInstruction("instruction 1");
        recipeInstructionRequestTest.setStep(2);

        recipeIngredientRequests.add(recipeIngredientRequestTest);
        recipeInstructionRequests.add(recipeInstructionRequestTest);

        RecipeRequest testRecipeReq = new RecipeRequest();
        testRecipeReq.setRecipeName("Test recipe");
        testRecipeReq.setRecipeIngredients(recipeIngredientRequests);
        testRecipeReq.setRecipeInstructions(recipeInstructionRequests);
        testRecipeReq.setTotalKiloCalories(200);

        mockMvc.perform(put(apiUrl + "/recipe").param("id", String.valueOf(recipeId))
                .content(objectMapper.writeValueAsString(testRecipeReq))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    void updateRecipeNotFound() throws Exception {

        when(recipeService.findById(5L)).thenReturn(ResponseEntity.notFound().build());

        verifyNoMoreInteractions(recipeService);

        List<RecipeIngredientRequest> recipeIngredientRequests = new ArrayList<>();
        List<RecipeInstructionRequest> recipeInstructionRequests = new ArrayList<>();

        RecipeIngredientRequest recipeIngredientRequestTest = new RecipeIngredientRequest();
        recipeIngredientRequestTest.setIngredientName("ingredient 1");
        recipeIngredientRequestTest.setIngredientAmount("2 tblspns");

        RecipeInstructionRequest recipeInstructionRequestTest = new RecipeInstructionRequest();
        recipeInstructionRequestTest.setInstruction("instruction 1");
        recipeInstructionRequestTest.setStep(2);

        recipeIngredientRequests.add(recipeIngredientRequestTest);
        recipeInstructionRequests.add(recipeInstructionRequestTest);

        RecipeRequest testRecipeReq = new RecipeRequest();
        testRecipeReq.setRecipeName("Test recipe");
        testRecipeReq.setRecipeIngredients(recipeIngredientRequests);
        testRecipeReq.setRecipeInstructions(recipeInstructionRequests);
        testRecipeReq.setTotalKiloCalories(200);




        mockMvc.perform(put(apiUrl + "/recipe").param("id", "5")
                        .content(objectMapper.writeValueAsString(testRecipeReq))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());


    }
}