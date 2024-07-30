package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
class WorkoutRepositoryTest {
    @Autowired
    private MuscleGroupRepository muscleGroupRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @BeforeEach
    public void setUp() throws Exception {
        muscleGroupRepository.deleteAll();
        workoutRepository.deleteAll();
    }
    @Test
    void findByWorkoutNameIgnoreCase() {

        MuscleGroup biceps = new MuscleGroup("Biceps", "Arm muscles");
        Workout dumbellCurls = new Workout("Dumbell curls", 100);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);
        biceps.addWorkout(dumbellCurls);
        dumbellCurls.addMuscleGroup(biceps.getId());
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);

        MuscleGroup pecs = new MuscleGroup("Pecs", "Chest muscles");
        Workout benchpress = new Workout("Bench press", 200);
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);
        pecs.addWorkout(benchpress);
        benchpress.addMuscleGroup(pecs.getId());
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);

        MuscleGroup quads = new MuscleGroup("Quads", "Hind muscles");
        Workout squats = new Workout("Squats", 300);
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);
        quads.addWorkout(squats);
        squats.addMuscleGroup(quads.getId());
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);

        assertThat(workoutRepository.findAll().size()).isEqualTo(3);

        Workout backend = workoutRepository.findByWorkoutNameIgnoreCase("squats").get();
        assertThat(backend.getWorkoutName()).isEqualTo("Squats");

        Workout benchPress = workoutRepository.findByWorkoutNameIgnoreCase("bench press").get();
        assertThat(benchPress.getWorkoutName()).isEqualTo("Bench press");

        assertThat(workoutRepository.findByWorkoutNameIgnoreCase("zbvzibvzvb").isEmpty()).isTrue();
    }

    @Test
    void findByBurnedCaloriesBetween() {

        MuscleGroup biceps = new MuscleGroup("Biceps", "Arm muscles");
        Workout dumbellCurls = new Workout("Dumbell curls", 100);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);
        biceps.addWorkout(dumbellCurls);
        dumbellCurls.addMuscleGroup(biceps.getId());
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);

        MuscleGroup pecs = new MuscleGroup("Pecs", "Chest muscles");
        Workout benchpress = new Workout("Bench press", 201);
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);
        pecs.addWorkout(benchpress);
        benchpress.addMuscleGroup(pecs.getId());
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);

        MuscleGroup quads = new MuscleGroup("Quads", "Hind muscles");
        Workout squats = new Workout("Squats", 299);
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);
        quads.addWorkout(squats);
        squats.addMuscleGroup(quads.getId());
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);

        assertThat(workoutRepository.findByBurnedKiloCaloriesPHourBetween(200, 300).size()).isEqualTo(2);
        assertThat(workoutRepository.findByBurnedKiloCaloriesPHourBetween(200, 300).get(0).getWorkoutName()).isEqualTo("Bench press");
        assertThat(workoutRepository.findByBurnedKiloCaloriesPHourBetween(200, 300).get(1).getWorkoutName()).isEqualTo("Squats");


        assertThat(workoutRepository.findByBurnedKiloCaloriesPHourBetween(0, 10).isEmpty()).isTrue();
        assertThat(workoutRepository.findByBurnedKiloCaloriesPHourBetween(10, 0).isEmpty()).isTrue();
    }

    @Test
    void existsByWorkoutName() {

        MuscleGroup biceps = new MuscleGroup("Biceps", "Arm muscles");
        Workout dumbellCurls = new Workout("Dumbell curls", 100);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);
        biceps.addWorkout(dumbellCurls);
        dumbellCurls.addMuscleGroup(biceps.getId());
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);

        MuscleGroup pecs = new MuscleGroup("Pecs", "Chest muscles");
        Workout benchpress = new Workout("Bench press", 200);
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);
        pecs.addWorkout(benchpress);
        benchpress.addMuscleGroup(pecs.getId());
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);

        MuscleGroup quads = new MuscleGroup("Quads", "Hind muscles");
        Workout squats = new Workout("Squats", 300);
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);
        quads.addWorkout(squats);
        squats.addMuscleGroup(quads.getId());
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);

        assertThat(workoutRepository.existsByWorkoutName("Dumbell curls")).isTrue();
        assertThat(workoutRepository.existsByWorkoutName("Bench press")).isTrue();
        assertThat(workoutRepository.existsByWorkoutName("Squats")).isTrue();

    }


    @Test
    void deleteByWorkoutId() {

        MuscleGroup biceps = new MuscleGroup("Biceps", "Arm muscles");
        Workout dumbellCurls = new Workout("Dumbell curls", 100);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);
        biceps.addWorkout(dumbellCurls);
        dumbellCurls.addMuscleGroup(biceps.getId());
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);

        MuscleGroup pecs = new MuscleGroup("Pecs", "Chest muscles");
        Workout benchpress = new Workout("Bench press", 200);
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);
        pecs.addWorkout(benchpress);
        benchpress.addMuscleGroup(pecs.getId());
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);

        MuscleGroup quads = new MuscleGroup("Quads", "Hind muscles");
        Workout squats = new Workout("Squats", 300);
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);
        quads.addWorkout(squats);
        squats.addMuscleGroup(quads.getId());
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);

        assertThat(workoutRepository.findAll().size()).isEqualTo(3);
        assertThat(workoutRepository.findAll().get(0).getWorkoutName()).isEqualTo("Dumbell curls");
        assertThat(workoutRepository.findAll().get(1).getWorkoutName()).isEqualTo("Bench press");
        assertThat(workoutRepository.findAll().get(2).getWorkoutName()).isEqualTo("Squats");

        biceps.getMuscleGroupWorkouts().remove(dumbellCurls);
        dumbellCurls.getWorkoutMuscleGroups().remove(biceps);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);

        workoutRepository.deleteWorkoutById(dumbellCurls.getId());

        assertThat(workoutRepository.findAll().size()).isEqualTo(2);
        assertThat(workoutRepository.findAll().get(0).getWorkoutName()).isEqualTo("Bench press");
        assertThat(workoutRepository.findAll().get(1).getWorkoutName()).isEqualTo("Squats");
    }

}