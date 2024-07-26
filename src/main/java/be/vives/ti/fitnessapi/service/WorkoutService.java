package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.repository.MuscleGroupRepository;
import be.vives.ti.fitnessapi.repository.WorkoutRepository;
import be.vives.ti.fitnessapi.request.WorkoutRequest;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import be.vives.ti.fitnessapi.response.WorkoutResponse;
import jakarta.transaction.Transactional;
import org.hibernate.jdbc.Work;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final MuscleGroupRepository muscleGroupRepository;

    public WorkoutService(WorkoutRepository workoutRepository, MuscleGroupRepository muscleGroupRepository) {
            this.workoutRepository = workoutRepository;
            this.muscleGroupRepository = muscleGroupRepository;
    }


    public ResponseEntity<WorkoutResponse> findById(Long id) {
        Optional<Workout> workout = workoutRepository.findById(id);
        if(workout.isPresent()) {
            WorkoutResponse response = new WorkoutResponse(workout.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    public List<WorkoutResponse> findAll() {
        List<Workout> workouts = workoutRepository.findAll();
        return workouts.stream().map(WorkoutResponse::new).collect(Collectors.toList());
    }

    public ResponseEntity<WorkoutResponse> findByExactWorkoutName(String workoutName) {
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
    public ResponseEntity<String> deleteById(Long id) {
        Optional<Workout> workout = workoutRepository.findById(id);

        if (workout.isPresent()) {
            List<MuscleGroup> muscleGroups = new ArrayList<>(workout.get().getWorkoutMuscleGroups());

            for (MuscleGroup muscleGroup : muscleGroups) {
                muscleGroup.removeWorkoutById(workout.get().getId());
                muscleGroupRepository.save(muscleGroup);
            }

            workoutRepository.deleteById(id);
            return ResponseEntity.ok("Workout '" + workout.get().getWorkoutName() + "' successfully deleted.");
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<Object> saveWorkout(WorkoutRequest savedWorkout) {
        if(workoutRepository.existsByWorkoutName(savedWorkout.getWorkoutName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Workout already exists.");
        }

        Workout mySavedWorkout = new Workout(
                savedWorkout.getWorkoutName(),
                savedWorkout.getBurnedKiloCaloriesPHour()
        );

        List<MuscleGroup> muscleGroups = new ArrayList<>();
        List<Long> muscleGroupIds = savedWorkout.getMuscleGroupIds();
        for (Long muscleGroupId : muscleGroupIds) {
            Optional<MuscleGroup> muscleGroup = muscleGroupRepository.findById(muscleGroupId);
            if(muscleGroup.isPresent()) {
                muscleGroups.add(muscleGroup.get());

            }
            else {
                return ResponseEntity.badRequest().body("Muscle group does not exist.");
            }
        }

        mySavedWorkout.setMuscleGroups(muscleGroups);

        workoutRepository.save(mySavedWorkout);

        for (MuscleGroup muscleGroup : muscleGroups) {
            muscleGroup.getMuscleGroupWorkouts().add(mySavedWorkout);
            muscleGroupRepository.save(muscleGroup);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().queryParam("id", mySavedWorkout.getId())
                .buildAndExpand(mySavedWorkout.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    public ResponseEntity<String> updateWorkout(Long id, WorkoutRequest updatedWorkout) {
        Optional<Workout> foundWorkout = workoutRepository.findById(id);

        if (foundWorkout.isPresent()) {
            Workout myUpdatedWorkout = foundWorkout.get();
            myUpdatedWorkout.setWorkoutName(updatedWorkout.getWorkoutName());
            myUpdatedWorkout.setBurnedKiloCaloriesPHour(updatedWorkout.getBurnedKiloCaloriesPHour());

            List<MuscleGroup> muscleGroups = new ArrayList<>();
            List<Long> muscleGroupIds = updatedWorkout.getMuscleGroupIds();

            for (Long muscleGroupId : muscleGroupIds) {
                Optional<MuscleGroup> muscleGroup = muscleGroupRepository.findById(muscleGroupId);
                if (muscleGroup.isPresent()) {
                    muscleGroups.add(muscleGroup.get());
                } else {
                    return ResponseEntity.badRequest().body("Muscle group does not exist.");
                }
            }

            for (MuscleGroup muscleGroup : myUpdatedWorkout.getWorkoutMuscleGroups()) {
                muscleGroup.getMuscleGroupWorkouts().remove(myUpdatedWorkout);
                muscleGroupRepository.save(muscleGroup);
            }
            myUpdatedWorkout.getWorkoutMuscleGroups().clear();

            myUpdatedWorkout.setMuscleGroups(muscleGroups);
            workoutRepository.save(myUpdatedWorkout);

            for (MuscleGroup muscleGroup : muscleGroups) {
                muscleGroup.getMuscleGroupWorkouts().add(myUpdatedWorkout);
                muscleGroupRepository.save(muscleGroup);
            }

            return ResponseEntity.ok("Workout successfully updated.");
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
