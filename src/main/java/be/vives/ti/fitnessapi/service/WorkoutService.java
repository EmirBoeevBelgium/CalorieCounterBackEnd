package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.repository.MuscleGroupRepository;
import be.vives.ti.fitnessapi.repository.WorkoutRepository;
import be.vives.ti.fitnessapi.request.WorkoutRequest;
import be.vives.ti.fitnessapi.response.WorkoutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    //Get all workouts
    public List<WorkoutResponse> findAll() {
        List<Workout> workouts = workoutRepository.findAll();
        return workouts.stream().map(WorkoutResponse::new).collect(Collectors.toList());
    }

    //Get exact workout by name
    public ResponseEntity<WorkoutResponse> findByExactWorkoutName(String workoutName) {
        Optional<Workout> workout = workoutRepository.findByWorkoutNameIgnoreCase(workoutName);
        //Check if workout exists
        if(workout.isPresent()) {
            //Convert to WorkoutResponse
            WorkoutResponse response = new WorkoutResponse(workout.get());
            //Return 200 with response
            return ResponseEntity.ok(response);
        }
        //Return 404 if not found
        return ResponseEntity.notFound().build();
    }

    //Get list of workouts which hourly burns kilocalories between a specified set
    public List<WorkoutResponse> findByBurnedKiloCaloriesBetween(double startCalorie, double endCalorie) {
        List<Workout> workouts = workoutRepository.findByBurnedKiloCaloriesPHourBetween(startCalorie, endCalorie);
        return workouts.stream().map(WorkoutResponse::new).collect(Collectors.toList());
    }

    //Delete workout by ID
    @Transactional
    public ResponseEntity<String> deleteById(String id) {
        Optional<Workout> workout = workoutRepository.findById(id);
        //Check if workout exists
        if (workout.isPresent()) {
            List<String> muscleGroups = new ArrayList<>(workout.get().getWorkoutMuscleGroupIds());
            //For each muscle group that is being deleted,
            //also delete the workout of this context from those muscle groups
            for (String muscleGroupId : muscleGroups) {
                MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupId).get();
                muscleGroup.removeWorkoutById(workout.get().getId());
                muscleGroupRepository.save(muscleGroup);
            }
            //Delete the workout
            workoutRepository.deleteById(id);
            //Return 200
            return ResponseEntity.ok("Workout '" + workout.get().getWorkoutName() + "' successfully deleted.");
        }
        //Return 404 if workout is not found
        return ResponseEntity.notFound().build();

    }

    //Save new workout
    public ResponseEntity<Object> saveWorkout(WorkoutRequest savedWorkout) {
        //Check by name if workout already exists
        if(workoutRepository.existsByWorkoutName(savedWorkout.getWorkoutName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Workout already exists.");
        }

        //Save the workout in a local variable
        Workout mySavedWorkout = new Workout(
                savedWorkout.getWorkoutName(),
                savedWorkout.getBurnedKiloCaloriesPHour()
        );

        //Initialize new arraylist of muscle groups
        List<MuscleGroup> muscleGroups = new ArrayList<>();

        //Get the ID's of the muscle groups of the saved workout
        List<String> muscleGroupIds = savedWorkout.getMuscleGroupIds();
        //Look up the muscle groups by id and save them in the muscle groups ArrayList
        for (String muscleGroupId : muscleGroupIds) {
            Optional<MuscleGroup> muscleGroup = muscleGroupRepository.findById(muscleGroupId);
            if(muscleGroup.isPresent()) {
                muscleGroups.add(muscleGroup.get());

            }
            else {
                //If the "to be saved" workout contains a muscle group ID that does not exist,
                //return 400 status code
                return ResponseEntity.badRequest().body("Muscle group does not exist.");
            }
        }

        //Save the muscle groups to the "to be saved" workout
        mySavedWorkout.setMuscleGroups(muscleGroupIds);

        //Save the workout
        workoutRepository.save(mySavedWorkout);

        //Add the workout to each muscle group also
        for (MuscleGroup muscleGroup : muscleGroups) {
            muscleGroup.getMuscleGroupWorkouts().add(mySavedWorkout);
            muscleGroupRepository.save(muscleGroup);
        }

        //Get the URI location of the new workout
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().queryParam("id", mySavedWorkout.getId())
                .buildAndExpand(mySavedWorkout.getId()).toUri();
        //Return the location
        return ResponseEntity.created(location).build();
    }


    //Update the workout
    @Transactional
    public ResponseEntity<String> updateWorkout(String id, WorkoutRequest updatedWorkout) {
        Optional<Workout> foundWorkout = workoutRepository.findById(id);

        //Check if workout exists
        if (foundWorkout.isPresent()) {
            Workout myUpdatedWorkout = foundWorkout.get();
            //Set name and burnable kiloCalories per hour
            myUpdatedWorkout.setWorkoutName(updatedWorkout.getWorkoutName());
            myUpdatedWorkout.setBurnedKiloCaloriesPHour(updatedWorkout.getBurnedKiloCaloriesPHour());

            //Set up muscle group ArrayList
            List<MuscleGroup> muscleGroups = new ArrayList<>();
            //Get the ID's of the muscle groups of the updated workout
            List<String> muscleGroupIds = updatedWorkout.getMuscleGroupIds();

            //Look up the muscle groups by id and save them in the muscle groups ArrayList
            for (String muscleGroupId : muscleGroupIds) {
                Optional<MuscleGroup> muscleGroup = muscleGroupRepository.findById(muscleGroupId);
                if (muscleGroup.isPresent()) {
                    muscleGroups.add(muscleGroup.get());
                } else {
                    //If the updated workout contains a muscle group ID that does not exist,
                    //return 400 status code
                    return ResponseEntity.badRequest().body("Muscle group does not exist.");
                }
            }

            //If there were any muscle groups removed from the workout, the workout would also need to be removed from
            //the muscle groups
            //Here I remove the workout from all muscle groups and add them back in if the workout contains the ID
            //of the muscle group
            for (String muscleGroupId : myUpdatedWorkout.getWorkoutMuscleGroupIds()) {
                MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupId).get();
                muscleGroup.getMuscleGroupWorkouts().remove(myUpdatedWorkout);
                muscleGroupRepository.save(muscleGroup);
            }
            //Clear the muscle group ID's
            myUpdatedWorkout.getWorkoutMuscleGroupIds().clear();

            //Set the muscle group ID's
            myUpdatedWorkout.setMuscleGroups(muscleGroupIds);
            workoutRepository.save(myUpdatedWorkout);

            //Also add the updated workout to the muscle groups of the workout
            for (MuscleGroup muscleGroup : muscleGroups) {
                muscleGroup.getMuscleGroupWorkouts().add(myUpdatedWorkout);
                muscleGroupRepository.save(muscleGroup);
            }

            //Return 200
            return ResponseEntity.ok("Workout successfully updated.");
        }

        //return 404 if workout is not found
        return ResponseEntity.notFound().build();
    }


    //Remove muscle group from workout
    public ResponseEntity<String> removeMuscleGroup(String workoutId, String muscleGroupId) {
        Optional<Workout> foundWorkout = workoutRepository.findById(workoutId);

        //Check if workout exists
        if(foundWorkout.isPresent()) {
            //Save workout in local variable
            Workout workout = foundWorkout.get();

            //Get muscle group by id
            MuscleGroup foundMusclegroup = muscleGroupRepository.findById(muscleGroupId).get();
            //Remove the muscle group from the workout
            workout.removeMuscleGroupById(muscleGroupId);

            //Remove the workout from the muscle group
            foundMusclegroup.removeWorkoutById(workoutId);

            //Update the workout
            workoutRepository.save(workout);

            //Update the muscle group
            muscleGroupRepository.save(foundMusclegroup);

            //Return status 200
            return ResponseEntity.ok("Muscle group '" + foundMusclegroup.getMuscleGroupName() + "' succesfully removed from workout.");
        }
        //Return 404 if not found
        return ResponseEntity.notFound().build();
    }

}
