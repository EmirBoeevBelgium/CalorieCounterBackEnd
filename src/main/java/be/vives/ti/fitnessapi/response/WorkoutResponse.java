package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;

import java.util.List;
import java.util.stream.Collectors;

public class WorkoutResponse {
    private String id;

    private String workoutName;

    private double burnedKiloCaloriesPHour;

    private List<String> muscleGroupIds;

    public WorkoutResponse(Workout workout) {
        this.id = workout.getId();
        this.workoutName = workout.getWorkoutName();
        this.burnedKiloCaloriesPHour = workout.getBurnedKiloCaloriesPHour();
        this.muscleGroupIds = workout.getWorkoutMuscleGroupIds();//.stream().map(WorkoutMuscleGroupResponse::new).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public double getBurnedKiloCaloriesPHour() {
        return burnedKiloCaloriesPHour;
    }

   public List<String> getWorkoutMuscleGroupIds() {
        return muscleGroupIds;
    }

}
