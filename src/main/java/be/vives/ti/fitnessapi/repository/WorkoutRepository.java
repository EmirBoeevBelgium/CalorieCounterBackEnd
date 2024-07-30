package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.Workout;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends MongoRepository<Workout, String> {

    Optional<Workout> findByWorkoutNameIgnoreCase(String workoutName);
    List<Workout> findByBurnedKiloCaloriesPHourBetween(double startBurnedCaloriesPHour, double endBurnedCaloriesPHour);

    boolean existsByWorkoutName(String workoutname);

    void deleteWorkoutById(String id);

}
