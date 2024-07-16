package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
    Optional<MuscleGroup> findByMuscleGroupName(String muscleGroupName);
}
