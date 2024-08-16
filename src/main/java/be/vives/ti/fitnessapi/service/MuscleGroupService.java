package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.repository.MuscleGroupRepository;
import be.vives.ti.fitnessapi.response.MuscleGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MuscleGroupService {
    private final MuscleGroupRepository muscleGroupRepository;

    public MuscleGroupService(MuscleGroupRepository muscleGroupRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
    }



    //Get all muscle groups
    public List<MuscleGroupResponse> findAll() {
        List<MuscleGroup> muscleGroups = muscleGroupRepository.findAll();
        //Map the muscle groups to the MuscleGroupResponse model
        return muscleGroups.stream().map(MuscleGroupResponse::new).collect(Collectors.toList());
    }

    //Get muscle group by ID
    public ResponseEntity<MuscleGroupResponse> findById(String id) {
        Optional<MuscleGroup> muscleGroupOptional = muscleGroupRepository.findById(id);
        //Check if muscle group exists
        if(muscleGroupOptional.isPresent()) {
            //Convert it to MuscleGroupResponse
            MuscleGroupResponse response = new MuscleGroupResponse(muscleGroupOptional.get());
            //Return status 200 with the response
            return ResponseEntity.ok(response);
        }
        //If it's not found return 404
        return ResponseEntity.notFound().build();
    }

    //Get exact muscle group by name
    public ResponseEntity<MuscleGroupResponse> findByExactMuscleGroupName(String muscleGroupName) {
        Optional<MuscleGroup> muscleGroup = muscleGroupRepository.findByMuscleGroupNameIgnoreCase(muscleGroupName);
        //Check if muscle group exists
        if(muscleGroup.isPresent()) {
            //Convert to MuscleGroupResponse
            MuscleGroupResponse response = new MuscleGroupResponse(muscleGroup.get());
            //Return status 200 with the response
            return ResponseEntity.ok(response);
        }
        //Return 404 if not found
        return ResponseEntity.notFound().build();
    }

    //Remove workout from muscle group
    public ResponseEntity<String> removeWorkout(String muscleGroupId, String workoutId) {
        Optional<MuscleGroup> foundMuscleGroup = muscleGroupRepository.findById(muscleGroupId);

        //Check if muscle group exists
        if(foundMuscleGroup.isPresent()) {
            //Save found muscle group in a variable
            MuscleGroup muscleGroup = foundMuscleGroup.get();
            //Remove the workout from said muscle group
            Workout foundWorkout = muscleGroup.removeWorkoutById(workoutId);

            //Save the updated muscle group
            muscleGroupRepository.save(muscleGroup);
            //Return success message
            return ResponseEntity.ok("Workout '" + foundWorkout.getWorkoutName() + "' succesfully removed from muscle group.");
        }
        //Return 404 if muscle group is not found
        return ResponseEntity.notFound().build();
    }
}
