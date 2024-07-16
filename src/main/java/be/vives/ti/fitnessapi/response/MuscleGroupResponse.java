package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.MuscleGroup;

import java.util.List;
import java.util.stream.Collectors;

public class MuscleGroupResponse {
    private Long id;

    private String muscleGroupName;

    private String muscleGroupDescription;

    private List<WorkoutResponse> workouts;

    public MuscleGroupResponse(MuscleGroup muscleGroup) {
        this.id = muscleGroup.getId();
        this.muscleGroupName = muscleGroup.getMuscleGroupName();
        this.muscleGroupDescription = muscleGroup.getMuscleGroupDescription();
        this.workouts = muscleGroup.getMuscleGroupWorkouts().stream().map(WorkoutResponse::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getMuscleGroupName() {
        return muscleGroupName;
    }

    public String getMuscleGroupDescription() {
        return muscleGroupDescription;
    }

    public List<WorkoutResponse> getWorkouts() {
        return workouts;
    }
}
