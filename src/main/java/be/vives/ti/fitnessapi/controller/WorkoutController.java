package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.request.WorkoutRequest;
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

    //GET ALL WORKOUTS
    @GetMapping
    public List<WorkoutResponse> findAll() {
        return workoutService.findAll();
    }

    //GET EXACT WORKOUT BY NAME
    @GetMapping("workout")
    public ResponseEntity<WorkoutResponse> findByExactWorkoutName(@RequestParam("name") String workoutName) {
        return workoutService.findByExactWorkoutName(workoutName);
    }

    //GET LIST OF WORKOUTS BETWEEN SPECIFIED KILOCALORIES
    @GetMapping("calories")
    public List<WorkoutResponse> findByBurnedCaloriesBetween(
            @RequestParam("startkilocalories") double startKiloCalories,
            @RequestParam("endkilocalories") double endKiloCalories) {
        return workoutService.findByBurnedKiloCaloriesBetween(startKiloCalories, endKiloCalories);
    }

    //DELETE WORKOUT BY ID
    @DeleteMapping("workout")
    public ResponseEntity<String> deleteById(@RequestParam("id") String id) {
        return workoutService.deleteById(id);
    }

    //SAVE WORKOUT
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveWorkout(@Valid @RequestBody WorkoutRequest savedWorkout) {
        return workoutService.saveWorkout(savedWorkout);
    }

    //UPDATE WORKOUT
    @PutMapping("workout")
    public ResponseEntity<String> updateWorkout(@RequestParam("id") String id, @Valid @RequestBody WorkoutRequest updatedWorkout) {
        return workoutService.updateWorkout(id, updatedWorkout);
    }

    //REMOVE MUSCLEGROUP FROM WORKOUT AND UPDATE
    @PutMapping("musclegroup")
    public ResponseEntity<String> removeMuscleGroup(@RequestParam("workoutid") String workoutId, @RequestParam("musclegroupid") String muscleGroupId) {
        return workoutService.removeMuscleGroup(workoutId, muscleGroupId);
    }
}
