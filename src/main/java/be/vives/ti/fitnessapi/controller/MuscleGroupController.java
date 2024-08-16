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


    //GET ALL
    @GetMapping
    public List<MuscleGroupResponse> findAll() {
        return muscleGroupService.findAll();
    }


    //GET BY ID
    @GetMapping("id")
    public ResponseEntity<MuscleGroupResponse> findById(@RequestParam("id") String id) {
        return muscleGroupService.findById(id);
    }

    //GET BY EXACT MUSCLEGROUPNAME
    @GetMapping("musclegroup")
    public ResponseEntity<MuscleGroupResponse> findByExactMuscleGroupName(@RequestParam("name") String muscleGroupName) {
        return muscleGroupService.findByExactMuscleGroupName(muscleGroupName);
    }


    //REMOVE WORKOUT FROM MUSCLEGROUP AND UPDATE
    @PutMapping("workout")
    public ResponseEntity<String> removeWorkoutFromMuscleGroup(@RequestParam("musclegroupid") String muscleGroupId, @RequestParam("workoutid") String workoutId) {
        return muscleGroupService.removeWorkout(muscleGroupId, workoutId);
    }
}
