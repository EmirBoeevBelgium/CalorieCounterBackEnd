package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MUSCLEGROUP")
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String muscleGroupName;

    @NotNull
    @Column(length = 1000)
    private String muscleGroupDescription;


    @ManyToMany
    @JoinTable(
            name = "musclegroup_workout",
            joinColumns = @JoinColumn(name = "musclegroup_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    private List<Workout> musclegroupWorkouts = new ArrayList<>();


    public MuscleGroup(String muscleGroupName, String muscleGroupDescription) {
        this.muscleGroupName = muscleGroupName;
        this.muscleGroupDescription = muscleGroupDescription;
    }

    protected MuscleGroup() {

    }

    public Long getId() {
        return id;
    }

    public String getMuscleGroupName() {
        return muscleGroupName;
    }

    public String getMuscleGroupDescription() {
        return muscleGroupDescription;
    }

    public List<Workout> getMuscleGroupWorkouts() {
        return musclegroupWorkouts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMuscleGroupName(String muscleGroupName) {
        this.muscleGroupName = muscleGroupName;
    }

    public void setMuscleGroupDescription(String muscleGroupDescription) {
        this.muscleGroupDescription = muscleGroupDescription;
    }

    public void addWorkout(Workout workout) {
        this.musclegroupWorkouts.add(workout);
    }

    @Override
    public String toString() {
        return "MuscleGroup{" +
                "id=" + id +
                ", muscleGroupName='" + muscleGroupName + '\'' +
                ", muscleGroupDescription='" + muscleGroupDescription + '\'' +
                '}';
    }
}
