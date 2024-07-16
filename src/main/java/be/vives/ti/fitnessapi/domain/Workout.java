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
    private Float burnedCaloriesPHour;

    @NotNull
    private Float burnedCaloriesPMinute;


    public Long getId() {
        return id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public Float getBurnedCaloriesPHour() {
        return burnedCaloriesPHour;
    }

    public Float getBurnedCaloriesPMinute() {
        return burnedCaloriesPMinute;
    }
}
