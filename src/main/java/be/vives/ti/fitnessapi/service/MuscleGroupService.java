package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.repository.MuscleGroupRepository;
import be.vives.ti.fitnessapi.response.MuscleGroupResponse;
import org.springframework.http.HttpStatus;
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


    public List<MuscleGroupResponse> findAll() {
        List<MuscleGroup> muscleGroups = muscleGroupRepository.findAll();
        return muscleGroups.stream().map(MuscleGroupResponse::new).collect(Collectors.toList());
    }

    public ResponseEntity<MuscleGroupResponse> findByMuscleGroupName(String muscleGroupName) {
        Optional<MuscleGroup> muscleGroup = muscleGroupRepository.findByMuscleGroupNameContainingIgnoreCase(muscleGroupName);

        if(muscleGroup.isPresent()) {
            MuscleGroupResponse response = new MuscleGroupResponse(muscleGroup.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> removeWorkout(Long muscleGroupId, Long workoutId) {
        Optional<MuscleGroup> foundMuscleGroup = muscleGroupRepository.findById(muscleGroupId);

        if(foundMuscleGroup.isPresent()) {
            MuscleGroup muscleGroup = foundMuscleGroup.get();
            Workout foundWorkout = muscleGroup.removeWorkoutById(workoutId);

            muscleGroupRepository.save(muscleGroup);
            return ResponseEntity.ok("Workout '" + foundWorkout.getWorkoutName() + "' succesfully removed from muscle group.");
        }

        return ResponseEntity.notFound().build();
    }
}
