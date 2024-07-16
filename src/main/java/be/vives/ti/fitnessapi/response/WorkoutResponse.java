package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.Workout;

public class WorkoutResponse {
    private Long id;

    private String workoutName;

    private double burnedCaloriesPHour;
    private double burnedCaloriesPMinute;

    public WorkoutResponse(Workout workout) {
        this.id = workout.getId();
        this.workoutName = workout.getWorkoutName();
        this.burnedCaloriesPHour = workout.getBurnedCaloriesPHour();
        this.burnedCaloriesPMinute = workout.getBurnedCaloriesPMinute();
    }

    public Long getId() {
        return id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public double getBurnedCaloriesPHour() {
        return burnedCaloriesPHour;
    }

    public double getBurnedCaloriesPMinute() {
        return burnedCaloriesPMinute;
    }
}
