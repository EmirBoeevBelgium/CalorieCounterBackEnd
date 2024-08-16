package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.MuscleGroup;

import java.util.stream.Collectors;

//Response model of the muscle group model. This specific model was supposed to be used in the WorkoutResponse model because
//if the MuscleGroupResponse model was used instead, the application would crash because WorkoutResponse
//has a reference to MuscleGroupResponse and vice versa, which causes an endless loop.
//This model doesn't show the muscle groups of the workout
//For the moment this model isn't being used by WorkoutResponse. But it will be needed in future development.
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
