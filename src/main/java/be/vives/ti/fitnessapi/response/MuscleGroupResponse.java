package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.MuscleGroup;

import java.util.List;
import java.util.stream.Collectors;

public class MuscleGroupResponse {
    private String id;

    private String muscleGroupName;

    private String muscleGroupDescription;

    private List<MuscleGroupWorkoutResponse> workouts;

    public MuscleGroupResponse(MuscleGroup muscleGroup) {
        this.id = muscleGroup.getId();
        this.muscleGroupName = muscleGroup.getMuscleGroupName();
        this.muscleGroupDescription = muscleGroup.getMuscleGroupDescription();
        this.workouts = muscleGroup.getMuscleGroupWorkouts().stream().map(MuscleGroupWorkoutResponse::new).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public String getMuscleGroupName() {
        return muscleGroupName;
    }

    public String getMuscleGroupDescription() {
        return muscleGroupDescription;
    }

    public List<MuscleGroupWorkoutResponse> getWorkouts() { //ff
        return workouts;
    }
}
