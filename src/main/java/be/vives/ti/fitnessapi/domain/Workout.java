package be.vives.ti.fitnessapi.domain;

//import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*@Entity
@Table(name = "WORKOUT")*/
@Document("workouts")
public class Workout {

    @Id
    private String id;

    @NotNull
    @Field("workoutName")
    private String workoutName;

    @NotNull
    @Field("burnedKiloCaloriesPHour")
    private double burnedKiloCaloriesPHour;

    private List<String> workoutMuscleGroupIds = new ArrayList<>();

    public Workout(String workoutName, double burnedKiloCaloriesPHour) {
        this.workoutName = workoutName;
        this.burnedKiloCaloriesPHour = burnedKiloCaloriesPHour;
    }

    protected Workout() {
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

    public List<String> getWorkoutMuscleGroups() {
        return workoutMuscleGroupIds;
    }

    public String removeMuscleGroupById(String id) {
        String foundMuscleGroup = new String();
        int i = 0;
        boolean muscleGroupFound = false;
        while (i < workoutMuscleGroupIds.size() && !muscleGroupFound) {
            if (Objects.equals(workoutMuscleGroupIds.get(i), id)) {
                foundMuscleGroup = workoutMuscleGroupIds.get(i);
                workoutMuscleGroupIds.remove(i);
                //foundMuscleGroup.getMuscleGroupWorkouts().remove(this);
                muscleGroupFound = true;
            }
            i++;
        }
        return foundMuscleGroup;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setBurnedKiloCaloriesPHour(double burnedKiloCaloriesPHour) {
        this.burnedKiloCaloriesPHour = burnedKiloCaloriesPHour;
    }

    public void setMuscleGroups(List<String> muscleGroups) {
        this.workoutMuscleGroupIds = muscleGroups;
    }

    public void addMuscleGroup(String muscleGroupId) {
        this.workoutMuscleGroupIds.add(muscleGroupId);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", workoutName='" + workoutName + '\'' +
                ", burnedCaloriesPHour=" + burnedKiloCaloriesPHour +
                '}';
    }

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String workoutName;

    @NotNull
    private double burnedKiloCaloriesPHour;


    @ManyToMany(mappedBy = "musclegroupWorkouts")
    private List<MuscleGroup> workoutMuscleGroups = new ArrayList<>();

    public Workout(String workoutName, double burnedKiloCaloriesPHour) {
        this.workoutName = workoutName;
        this.burnedKiloCaloriesPHour = burnedKiloCaloriesPHour;
    }

    protected Workout() {

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


    public List<MuscleGroup> getWorkoutMuscleGroups() {
        return workoutMuscleGroups;
    }


    public MuscleGroup removeMuscleGroupById(Long id) {
        MuscleGroup foundMuscleGroup = new MuscleGroup();
        int i = 0;
        boolean musclegGroupFound = false;
        while (i < workoutMuscleGroups.size() && !musclegGroupFound) {
            if(Objects.equals(workoutMuscleGroups.get(i).getId(), id)) {
                foundMuscleGroup = workoutMuscleGroups.get(i);
                workoutMuscleGroups.remove(i);
                foundMuscleGroup.getMuscleGroupWorkouts().remove(this);
                musclegGroupFound = true;

            }
            i++;
        }
        return foundMuscleGroup;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setBurnedKiloCaloriesPHour(double burnedKiloCaloriesPHour) {
        this.burnedKiloCaloriesPHour = burnedKiloCaloriesPHour;
    }

    public void setMuscleGroups(List<MuscleGroup> muscleGroups) {
        this.workoutMuscleGroups = muscleGroups;
    }

    public void addMuscleGroup(MuscleGroup muscleGroup) {
        this.workoutMuscleGroups.add(muscleGroup);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", workoutName='" + workoutName + '\'' +
                ", burnedCaloriesPHour=" + burnedKiloCaloriesPHour +
                '}';
    }*/
}
