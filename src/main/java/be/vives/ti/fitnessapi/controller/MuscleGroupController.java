package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.response.MuscleGroupResponse;
import be.vives.ti.fitnessapi.service.MuscleGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/musclegroups", produces = "application/json")
public class MuscleGroupController {

    private MuscleGroupService muscleGroupService;

    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }


    @GetMapping
    public List<MuscleGroupResponse> findAll() {
        return muscleGroupService.findAll();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<MuscleGroupResponse> findByMuscleGroupName(@PathVariable("name") String muscleGroupName) {
        return muscleGroupService.findByMuscleGroupName(muscleGroupName);
    }
}
