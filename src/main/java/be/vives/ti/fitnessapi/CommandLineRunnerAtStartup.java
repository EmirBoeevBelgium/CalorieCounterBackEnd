package be.vives.ti.fitnessapi;

import be.vives.ti.fitnessapi.repository.RecipeRepository;
import be.vives.ti.fitnessapi.repository.WorkoutRepository;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerAtStartup {

    private final RecipeRepository recipeRepository;

    private final WorkoutRepository workoutRepository;
}
