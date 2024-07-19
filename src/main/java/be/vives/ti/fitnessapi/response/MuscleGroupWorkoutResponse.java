package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.Workout;

import java.util.stream.Collectors;

public class MuscleGroupWorkoutResponse {
    private Long id;

    private String workoutName;

    private double burnedKiloCaloriesPHour;

    public MuscleGroupWorkoutResponse(Workout workout) {
        this.id = workout.getId();
        this.workoutName = workout.getWorkoutName();
        this.burnedKiloCaloriesPHour = workout.getBurnedKiloCaloriesPHour();
    }
    public Long getId() {
        return id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public double getBurnedKiloCaloriesPHour() {
        return burnedKiloCaloriesPHour;
    }
}
