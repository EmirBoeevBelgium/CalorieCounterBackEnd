package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MuscleGroupRepositoryTest {

    @Autowired
    private MuscleGroupRepository muscleGroupRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Test
    public void simpleCrud() {
        MuscleGroup pectorals = new MuscleGroup("Pecs", "Chest muscles.");
        Workout benchPress = new Workout("Bench press", 200);
        benchPress.addMuscleGroup(pectorals);
        pectorals.addWorkout(benchPress);

        pectorals = muscleGroupRepository.save(pectorals);
        benchPress = workoutRepository.save(benchPress);

        assertThat(pectorals.getId()).isNotNull();
        assertThat(muscleGroupRepository.findAll().size()).isEqualTo(1);

        assertThat(pectorals.getMuscleGroupName()).isEqualTo("Pecs");
        assertThat(pectorals.getMuscleGroupDescription()).isEqualTo("Chest muscles.");
        assertThat(pectorals.getMuscleGroupWorkouts().size()).isEqualTo(1);
        assertThat(pectorals.getMuscleGroupWorkouts().get(0).getWorkoutName()).isEqualTo("Bench press");

        pectorals.setMuscleGroupName("Delt");
        pectorals.setMuscleGroupDescription("Neck muscles.");
        pectorals.removeWorkoutById(benchPress.getId());
        Workout barbellPress = new Workout("Barbell overhead press", 300);
        pectorals.addWorkout(barbellPress);
        barbellPress.addMuscleGroup(pectorals);

        barbellPress = workoutRepository.save(barbellPress);
        pectorals = muscleGroupRepository.save(pectorals);

        assertThat(pectorals.getMuscleGroupName()).isEqualTo("Delt");
        assertThat(pectorals.getMuscleGroupDescription()).isEqualTo("Neck muscles.");
        assertThat(pectorals.getMuscleGroupWorkouts().size()).isEqualTo(1);
        assertThat(pectorals.getMuscleGroupWorkouts().get(0).getWorkoutName()).isEqualTo(barbellPress.getWorkoutName());

        MuscleGroup biceps = new MuscleGroup("Biceps", "Frontal arm muscles.");
        Workout dumbellCurls = new Workout("Dumbell curls", 200);
        biceps.addWorkout(dumbellCurls);
        dumbellCurls.addMuscleGroup(biceps);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);
        assertThat(biceps.getId()).isNotNull();
        assertThat(muscleGroupRepository.findAll().size()).isEqualTo(2);

        MuscleGroup muscleGroupWithId = muscleGroupRepository.findById(biceps.getId()).get();
        assertThat(muscleGroupWithId.getId()).isEqualTo(biceps.getId());
        assertThat(muscleGroupWithId.getMuscleGroupName()).isEqualTo(biceps.getMuscleGroupName());

        muscleGroupRepository.delete(muscleGroupWithId);
        assertThat(muscleGroupRepository.findAll().size()).isEqualTo(1);
        assertThat(muscleGroupRepository.findById(biceps.getId()).isEmpty()).isTrue();
    }


    @Test
    void findByMuscleGroupNameIgnoreCase() {
        MuscleGroup biceps = new MuscleGroup("Biceps", "Arm muscles");
        Workout dumbellCurls = new Workout("Dumbell curls", 100);
        biceps.addWorkout(dumbellCurls);
        dumbellCurls.addMuscleGroup(biceps);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);

        MuscleGroup pecs = new MuscleGroup("Pecs", "Chest muscles");
        Workout benchpress = new Workout("Bench press", 200);
        pecs.addWorkout(benchpress);
        benchpress.addMuscleGroup(pecs);
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);

        MuscleGroup quads = new MuscleGroup("Quads", "Hind muscles");
        Workout squats = new Workout("Squats", 300);
        quads.addWorkout(squats);
        squats.addMuscleGroup(quads);
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);

        assertThat(muscleGroupRepository.findAll().size()).isEqualTo(3);

        MuscleGroup backend = muscleGroupRepository.findByMuscleGroupNameIgnoreCase("biceps").get();
        assertThat(backend.getMuscleGroupName()).isEqualTo("Biceps");

        MuscleGroup quadsSearch = muscleGroupRepository.findByMuscleGroupNameIgnoreCase("quads").get();
        assertThat(quadsSearch.getMuscleGroupName()).isEqualTo("Quads");

        assertThat(muscleGroupRepository.findByMuscleGroupNameIgnoreCase("zbvzibvzvb").isEmpty()).isTrue();
    }
}