package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.Workout;

//Response type of the workout model. This specific model is used in the MuscleGroupResponse model because
//if the WorkoutResponse model was used instead, the application would crash because WorkoutResponse
//has a reference to MuscleGroupResponse and vice versa, which causes an endless loop.
//This model doesn't show the muscle groups of the workout
public class MuscleGroupWorkoutResponse {
    private String id;

    private String workoutName;

    private double burnedKiloCaloriesPHour;

    public MuscleGroupWorkoutResponse(Workout workout) {
        this.id = workout.getId();
        this.workoutName = workout.getWorkoutName();
        this.burnedKiloCaloriesPHour = workout.getBurnedKiloCaloriesPHour();
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
}
