package be.vives.ti.fitnessapi.domain;

//import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
//import org.hibernate.jdbc.Work;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Entity
//@Table(name = "MUSCLEGROUP")
@Document("muscle groups")
public class MuscleGroup {

    @Id
    private String id;

    @NotNull
    @Field("muscleGroupName")
    private String muscleGroupName;

    @NotNull
    @Field("muscleGroupDescription")
    private String muscleGroupDescription;

    @DBRef
    private List<Workout> musclegroupWorkouts = new ArrayList<>();

    public MuscleGroup(String muscleGroupName, String muscleGroupDescription) {
        this.muscleGroupName = muscleGroupName;
        this.muscleGroupDescription = muscleGroupDescription;
    }

    protected MuscleGroup() {
    }

    public String getId() {
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

    public Workout removeWorkoutById(String id) {
        Workout foundWorkout = null;
        int i = 0;
        boolean workoutFound = false;
        while (i < musclegroupWorkouts.size() && !workoutFound) {
            if (Objects.equals(musclegroupWorkouts.get(i).getId(), id)) {
                foundWorkout = musclegroupWorkouts.get(i);
                musclegroupWorkouts.remove(i);
                foundWorkout.getWorkoutMuscleGroups().remove(this);
                workoutFound = true;
            }
            i++;
        }
        return foundWorkout;
    }

    public void setId(String id) {
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
        return String.format("MuscleGroup=[id='%s', muscleGroupName='%s', muscleGroupDescription='%s']", id, muscleGroupName, muscleGroupDescription);
    }

    /*@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Field
    private String muscleGroupName;

    @NotNull
    //@Column(length = 1000)
    @Field
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

    public Workout removeWorkoutById(Long id) {
        Workout foundWorkout = new Workout();
        int i = 0;
        boolean workoutFound = false;
        while (i < musclegroupWorkouts.size() && !workoutFound) {
            if(Objects.equals(musclegroupWorkouts.get(i).getId(), id)) {
                foundWorkout = musclegroupWorkouts.get(i);
                musclegroupWorkouts.remove(i);
                foundWorkout.getWorkoutMuscleGroups().remove(this);
                workoutFound = true;

            }
            i++;
        }
        return foundWorkout;
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

        return String.format("MuscleGroup=[id='%s', muscleGroupName='%s', muscleGroupDescription='%s']", id, muscleGroupName, muscleGroupDescription);
    }*/
}
