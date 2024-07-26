package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.request.WorkoutRequest;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import be.vives.ti.fitnessapi.response.WorkoutResponse;
import be.vives.ti.fitnessapi.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "workouts", produces = "application/json")
public class WorkoutController {
    private WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<WorkoutResponse> findAll() {
        return workoutService.findAll();
    }

    @GetMapping("workout")
    public ResponseEntity<WorkoutResponse> findByExactWorkoutName(@RequestParam("name") String workoutName) {
        return workoutService.findByExactWorkoutName(workoutName);
    }

    @GetMapping("calories")
    public List<WorkoutResponse> findByBurnedCaloriesBetween(
            @RequestParam("startkilocalories") double startKiloCalories,
            @RequestParam("endkilocalories") double endKiloCalories) {
        return workoutService.findByBurnedKiloCaloriesBetween(startKiloCalories, endKiloCalories);
    }

    @DeleteMapping("workout")
    public ResponseEntity<String> deleteById(@RequestParam("id") Long id) {
        return workoutService.deleteById(id);
    }

    //Moet nog functie voorzien dat kijkt of recept al bestaat.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveWorkout(@Valid @RequestBody WorkoutRequest savedWorkout) {
        return workoutService.saveWorkout(savedWorkout);
    }

    @PutMapping("workout")
    public ResponseEntity<String> updateWorkout(@RequestParam("id") Long id, @Valid @RequestBody WorkoutRequest updatedWorkout) {
        return workoutService.updateWorkout(id, updatedWorkout);
    }

    @PutMapping("musclegroup")
    public ResponseEntity<String> removeMuscleGroup(@RequestParam("workoutid") Long workoutId, @RequestParam("musclegroupid") Long muscleGroupId) {
        return workoutService.removeMuscleGroup(workoutId, muscleGroupId);
    }
}
