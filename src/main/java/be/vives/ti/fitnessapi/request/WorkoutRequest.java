package be.vives.ti.fitnessapi.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class WorkoutRequest {
    @NotNull
    @NotEmpty
    private String workoutName;

    @NotNull
    @Min(0)
    private double burnedKiloCaloriesPHour;

    @NotNull
    @NotEmpty
    private List<Long> muscleGroupIds;

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public double getBurnedKiloCaloriesPHour() {
        return burnedKiloCaloriesPHour;
    }

    public void setBurnedKiloCaloriesPHour(double burnedKiloCaloriesPHour) {
        this.burnedKiloCaloriesPHour = burnedKiloCaloriesPHour;
    }

    public List<Long> getMuscleGroupIds() {
        return muscleGroupIds;
    }

    public void setMuscleGroupIds(List<Long> muscleGroupIds) {
        this.muscleGroupIds = muscleGroupIds;
    }
}
