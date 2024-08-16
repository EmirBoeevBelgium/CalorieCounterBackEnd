package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.Workout;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends MongoRepository<Workout, String> {
    //Find exact workout by name, ignoring the case
    Optional<Workout> findByWorkoutNameIgnoreCase(String workoutName);
    //Find recipes between a specified set of burned kiloCalories/Hour
    List<Workout> findByBurnedKiloCaloriesPHourBetween(double startBurnedCaloriesPHour, double endBurnedCaloriesPHour);
    //Check if workout exists by name
    boolean existsByWorkoutName(String workoutname);
    //Delete workout by ID
    void deleteWorkoutById(String id);

}
