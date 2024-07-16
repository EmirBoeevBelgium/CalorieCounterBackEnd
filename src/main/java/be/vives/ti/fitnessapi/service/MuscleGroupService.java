package be.vives.ti.fitnessapi.service;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
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


    public List<MuscleGroupResponse> findAll() {
        List<MuscleGroup> muscleGroups = muscleGroupRepository.findAll();
        List<MuscleGroupResponse> muscleGroupResponses = muscleGroups.stream().map(MuscleGroupResponse::new).collect(Collectors.toList());
        return muscleGroupResponses;
    }

    public ResponseEntity<MuscleGroupResponse> findByMuscleGroupName(String muscleGroupName) {
        Optional<MuscleGroup> muscleGroup = muscleGroupRepository.findByMuscleGroupNameIgnoreCase(muscleGroupName);

        if(muscleGroup.isPresent()) {
            MuscleGroupResponse response = new MuscleGroupResponse(muscleGroup.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}
