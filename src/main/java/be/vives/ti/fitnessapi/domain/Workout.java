package be.vives.ti.fitnessapi.domain;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //Each workout can target one or multiple muscle groups. Here I save the ID's instead of the muscle groups or
    //else loading them would take too long (around 40 seconds or so). It might be because I am using a free tier of MongoDB
    //which has less computing power.
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

    public List<String> getWorkoutMuscleGroupIds() {
        return workoutMuscleGroupIds;
    }

    public String removeMuscleGroupById(String id) {
        String foundMuscleGroupId = new String();
        int i = 0;
        boolean muscleGroupFound = false;

        while (i < workoutMuscleGroupIds.size() && !muscleGroupFound) {
            //Check if the muscle group exists
            if (Objects.equals(workoutMuscleGroupIds.get(i), id)) {
                //Save the muscle group
                foundMuscleGroupId = workoutMuscleGroupIds.get(i);
                //Remove the muscle group
                workoutMuscleGroupIds.remove(i);
                //Set the variable muscleGroupFound to true, to stop the loop
                muscleGroupFound = true;
            }
            i++;
        }
        return foundMuscleGroupId;
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

    public void setMuscleGroups(List<String> muscleGroupIds) {
        this.workoutMuscleGroupIds = muscleGroupIds;
    }

    public void addMuscleGroup(MuscleGroup muscleGroup) {
        this.workoutMuscleGroupIds.add(muscleGroup.getId());
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", workoutName='" + workoutName + '\'' +
                ", burnedCaloriesPHour=" + burnedKiloCaloriesPHour +
                '}';
    }
}
