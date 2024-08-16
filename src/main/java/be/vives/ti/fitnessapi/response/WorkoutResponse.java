package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.Workout;

import java.util.List;

//Response type of the workout model.
public class WorkoutResponse {
    private String id;

    private String workoutName;

    private double burnedKiloCaloriesPHour;

    //Instead of returning a list of WorkoutMuscleGroupResponse, I return a list of ID's of the muscle groups
    //that the workout targets. Mapping the muscle groups in a workout and mapping the workouts in a muscle group
    //takes a bit time. This would cause a time-out error on the online deployed application. The ID's can be used to do
    //a lookup of the muscle groups that the workout targets. This is a temporary solution.
    private List<String> muscleGroupIds;

    public WorkoutResponse(Workout workout) {
        this.id = workout.getId();
        this.workoutName = workout.getWorkoutName();
        this.burnedKiloCaloriesPHour = workout.getBurnedKiloCaloriesPHour();
        this.muscleGroupIds = workout.getWorkoutMuscleGroupIds();
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
