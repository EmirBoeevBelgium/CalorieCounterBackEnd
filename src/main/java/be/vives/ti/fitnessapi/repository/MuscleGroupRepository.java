package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MuscleGroupRepository extends MongoRepository<MuscleGroup, String> {
    //Find exact muscle group by name, ignoring the case
    Optional<MuscleGroup> findByMuscleGroupNameIgnoreCase(String muscleGroupName);
}
