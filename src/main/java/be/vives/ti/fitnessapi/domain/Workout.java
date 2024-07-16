package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "musclegroupWorkouts")
    private List<MuscleGroup> muscleGroups = new ArrayList<>();

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

    public List<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setBurnedCaloriesPHour(double burnedCaloriesPHour) {
        this.burnedCaloriesPHour = burnedCaloriesPHour;
    }

    public void setBurnedCaloriesPMinute(double burnedCaloriesPMinute) {
        this.burnedCaloriesPMinute = burnedCaloriesPMinute;
    }


    public void addMuscleGroup(MuscleGroup muscleGroup) {
        this.muscleGroups.add(muscleGroup);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", workoutName='" + workoutName + '\'' +
                ", burnedCaloriesPHour=" + burnedCaloriesPHour +
                ", burnedCaloriesPMinute=" + burnedCaloriesPMinute +
                '}';
    }
}
