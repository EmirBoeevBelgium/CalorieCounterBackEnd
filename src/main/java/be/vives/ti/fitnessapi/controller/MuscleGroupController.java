package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.response.MuscleGroupResponse;
import be.vives.ti.fitnessapi.service.MuscleGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "musclegroups", produces = "application/json")
public class MuscleGroupController {

    private MuscleGroupService muscleGroupService;

    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }


    @GetMapping
    public List<MuscleGroupResponse> findAll() {
        return muscleGroupService.findAll();
    }

    @GetMapping("musclegroup")
    public ResponseEntity<MuscleGroupResponse> findByMuscleGroupName(@RequestParam("name") String muscleGroupName) {
        return muscleGroupService.findByMuscleGroupName(muscleGroupName);
    }

    @PutMapping("workout")
    public ResponseEntity<String> removeMuscleGroup(@RequestParam("musclegroupid") Long muscleGroupId, @RequestParam("workoutid") Long workoutId) {
        return muscleGroupService.removeWorkout(muscleGroupId, workoutId);
    }
}
