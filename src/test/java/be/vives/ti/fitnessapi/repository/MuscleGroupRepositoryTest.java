package be.vives.ti.fitnessapi.repository;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
class MuscleGroupRepositoryTest {

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
    public void simpleCrud() {
        MuscleGroup pectorals = new MuscleGroup("Pecs", "Chest muscles.");
        pectorals = muscleGroupRepository.save(pectorals);
        Workout benchPress = new Workout("Bench press", 200);

        benchPress = workoutRepository.save(benchPress);

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
        barbellPress = workoutRepository.save(barbellPress);
        pectorals.addWorkout(barbellPress);
        barbellPress.addMuscleGroup(pectorals);

        pectorals = muscleGroupRepository.save(pectorals);
        barbellPress = workoutRepository.save(barbellPress);

        assertThat(pectorals.getMuscleGroupName()).isEqualTo("Delt");
        assertThat(pectorals.getMuscleGroupDescription()).isEqualTo("Neck muscles.");
        assertThat(pectorals.getMuscleGroupWorkouts().size()).isEqualTo(1);
        assertThat(pectorals.getMuscleGroupWorkouts().get(0).getWorkoutName()).isEqualTo(barbellPress.getWorkoutName());

        MuscleGroup biceps = new MuscleGroup("Biceps", "Frontal arm muscles.");
        Workout dumbellCurls = new Workout("Dumbell curls", 200);
        biceps = muscleGroupRepository.save(biceps);
        dumbellCurls = workoutRepository.save(dumbellCurls);
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
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);
        biceps.addWorkout(dumbellCurls);
        dumbellCurls.addMuscleGroup(biceps);
        muscleGroupRepository.save(biceps);
        workoutRepository.save(dumbellCurls);

        MuscleGroup pecs = new MuscleGroup("Pecs", "Chest muscles");
        Workout benchpress = new Workout("Bench press", 200);
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);
        pecs.addWorkout(benchpress);
        benchpress.addMuscleGroup(pecs);
        muscleGroupRepository.save(pecs);
        workoutRepository.save(benchpress);

        MuscleGroup quads = new MuscleGroup("Quads", "Hind muscles");
        Workout squats = new Workout("Squats", 300);
        muscleGroupRepository.save(quads);
        workoutRepository.save(squats);
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