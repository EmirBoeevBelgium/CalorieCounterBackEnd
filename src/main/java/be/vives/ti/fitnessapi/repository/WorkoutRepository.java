package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findByWorkoutNameIgnoreCase(String workoutName);
    List<Workout> findByBurnedCaloriesPHourLessThanEqual(double burnedCaloriesPHour);
    List<Workout> findByBurnedCaloriesPHourGreaterThanEqual(double burnedCaloriesPHour);

    List<Workout> findByBurnedCaloriesPMinuteLessThanEqual(double burnedCaloriesPMinute);
    List<Workout> findByBurnedCaloriesPMinuteGreaterThanEqual(double burnedCaloriesPMinute);

    void deleteWorkoutById(Long id);


}
