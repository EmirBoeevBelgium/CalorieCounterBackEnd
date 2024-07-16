package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findByWorkoutNameIgnoreCase(String workoutName);
    List<Workout> findByBurnedCaloriesPHour(Float burnedCaloriesPHour);

    List<Workout> findByBurnedCaloriesPMinute(Float burnedCaloriesPMinute);

    void deleteWorkoutByWorkoutNameIgnoreCase(String workoutName);


}
