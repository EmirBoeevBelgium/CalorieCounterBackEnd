package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.repository.WorkoutRepository;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import be.vives.ti.fitnessapi.response.WorkoutResponse;
import jakarta.transaction.Transactional;
import org.hibernate.jdbc.Work;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
            this.workoutRepository = workoutRepository;
    }

    public List<WorkoutResponse> findAll() {
        List<Workout> workouts = workoutRepository.findAll();
        return workouts.stream().map(WorkoutResponse::new).collect(Collectors.toList());
    }

    public ResponseEntity<WorkoutResponse> findByWorkoutName(String workoutName) {
        Optional<Workout> workout = workoutRepository.findByWorkoutNameIgnoreCase(workoutName);
        if(workout.isPresent()) {
            WorkoutResponse response = new WorkoutResponse(workout.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    public List<WorkoutResponse> findByBurnedKiloCaloriesBetween(double startCalorie, double endCalorie) {
        List<Workout> workouts = workoutRepository.findByBurnedKiloCaloriesPHourBetween(startCalorie, endCalorie);
        return workouts.stream().map(WorkoutResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<Void> deleteById(Long id) {
        Optional<Workout> workout = workoutRepository.findById(id);

        if(workout.isPresent()) {
            workoutRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<String> saveWorkout(Workout savedWorkout) {
        if(workoutRepository.existsByWorkoutName(savedWorkout.getWorkoutName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Workout already exists.");
        }
        Workout workout = workoutRepository.save(savedWorkout);
        return ResponseEntity.ok("Workout " + workout.getWorkoutName() + " saved succesfully.");
    }

    public ResponseEntity<String> updateWorkout(Long id, Workout updatedWorkout) {
        Optional<Workout> foundWorkout = workoutRepository.findById(id);

        if(foundWorkout.isPresent()) {
            Workout workout = foundWorkout.get();
            workout.setWorkoutName(updatedWorkout.getWorkoutName());
            workout.setBurnedKiloCaloriesPHour(updatedWorkout.getBurnedKiloCaloriesPHour());
            workout.setMuscleGroups(updatedWorkout.getWorkoutMuscleGroups());

            workoutRepository.save(updatedWorkout);
            return ResponseEntity.ok("Workout succesfully updated.");
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> removeMuscleGroup(Long workoutId, Long muscleGroupId) {
        Optional<Workout> foundWorkout = workoutRepository.findById(workoutId);

        if(foundWorkout.isPresent()) {
            Workout workout = foundWorkout.get();
            MuscleGroup foundMusclegroup = workout.removeMuscleGroupById(muscleGroupId);

            workoutRepository.save(workout);
            return ResponseEntity.ok("Muscle group '" + foundMusclegroup.getMuscleGroupName() + "' succesfully removed from workout.");
        }

        return ResponseEntity.notFound().build();
    }

}
