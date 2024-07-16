package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "WORKOUT")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String workoutName;

    @NotNull
    private double burnedCaloriesPHour;

    @NotNull
    private double burnedCaloriesPMinute;


    public Workout(String workoutName, double burnedCaloriesPHour, double burnedCaloriesPMinute) {
        this.workoutName = workoutName;
        this.burnedCaloriesPHour = burnedCaloriesPHour;
        this.burnedCaloriesPMinute = burnedCaloriesPMinute;
    }

    protected Workout() {

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
