package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.MuscleGroup;

import java.util.stream.Collectors;

public class WorkoutMuscleGroupResponse {
    private String id;

    private String muscleGroupName;

    private String muscleGroupDescription;

    public WorkoutMuscleGroupResponse(MuscleGroup muscleGroup) {
        this.id = muscleGroup.getId();
        this.muscleGroupName = muscleGroup.getMuscleGroupName();
        this.muscleGroupDescription = muscleGroup.getMuscleGroupDescription();
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
}
