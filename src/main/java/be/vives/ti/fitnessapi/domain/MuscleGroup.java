package be.vives.ti.fitnessapi.domain;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //Each muscle group can be targeted by multiple workouts/exercises.
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
            //Check if the workout that needs to be removed from the muscle group actually exists
            if (Objects.equals(musclegroupWorkouts.get(i).getId(), id)) {
                //Save the found workout
                foundWorkout = musclegroupWorkouts.get(i);
                //Remove the found workout
                musclegroupWorkouts.remove(i);
                //Go to the workout and remove the muscle group from there
                foundWorkout.getWorkoutMuscleGroupIds().remove(this);
                //Set variable workoutFound to true to stop the loop
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

}
