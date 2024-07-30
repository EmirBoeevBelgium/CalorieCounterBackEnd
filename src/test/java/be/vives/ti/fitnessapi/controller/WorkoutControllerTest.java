package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.domain.*;
import be.vives.ti.fitnessapi.request.RecipeRequest;
import be.vives.ti.fitnessapi.request.WorkoutRequest;
import be.vives.ti.fitnessapi.response.MuscleGroupResponse;
import be.vives.ti.fitnessapi.response.RecipeResponse;
import be.vives.ti.fitnessapi.response.WorkoutResponse;
import be.vives.ti.fitnessapi.service.WorkoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(WorkoutController.class)
class WorkoutControllerTest {
    private final String apiUrl = "/workouts";

    @MockBean
    private WorkoutService workoutService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Workout> workouts;
    private List<MuscleGroup> workoutMuscleGroups;
    @BeforeEach
    void setUp() {
        workouts = new ArrayList<>();

        Workout benchPress = new Workout("Bench press", 520);
        Workout preacherCurls = new Workout("Preacher curls", 418);
        Workout barbellPress = new Workout("Overhead barbell press", 391);
        Workout cableChestPress = new Workout("Cable chest press", 220);

        workouts.add(benchPress);
        workouts.add(preacherCurls);
        workouts.add(barbellPress);
        workouts.add(cableChestPress);

        workoutMuscleGroups = new ArrayList<>();

        MuscleGroup biceps = new MuscleGroup("Biceps", "Double muscle in your arms.");
        MuscleGroup triceps = new MuscleGroup("Triceps", "Triple muscle in your arms.");
        MuscleGroup pecs = new MuscleGroup("Pectorals", "Chest muscles.");

        workoutMuscleGroups.add(biceps);
        workoutMuscleGroups.add(triceps);
        workoutMuscleGroups.add(pecs);

        benchPress.addMuscleGroup(triceps.getId());
        benchPress.addMuscleGroup(pecs.getId());

        preacherCurls.addMuscleGroup(biceps.getId());

        barbellPress.addMuscleGroup(triceps.getId());

        cableChestPress.addMuscleGroup(pecs.getId());

        when(workoutService.findAll()).thenReturn(workouts.stream().map(WorkoutResponse::new).collect(Collectors.toList()));
    }
    @Test
    void findAllWorkouts() throws Exception {
        when(workoutService.findAll()).thenReturn(workouts.stream().map(WorkoutResponse::new).collect(Collectors.toList()));
        mockMvc.perform(get(apiUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].workoutName", equalTo("Bench press")))
                .andExpect(jsonPath("$[1].workoutName", equalTo("Preacher curls")))
                .andExpect(jsonPath("$[2].workoutName", equalTo("Overhead barbell press")))
                .andExpect(jsonPath("$[3].workoutName", equalTo("Cable chest press")));
    }

    @Test
    void findByExactWorkoutNameUpperCase() throws Exception {
        WorkoutResponse benchPress = new WorkoutResponse(workouts.get(0));
        when(workoutService.findByExactWorkoutName("BENCH PRESS")).thenReturn(ResponseEntity.ok(benchPress));
        mockMvc.perform(get(apiUrl + "/workout").param("name", "BENCH PRESS"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workoutName", equalTo("Bench press")));
    }


    @Test
    void findByExactWorkoutNameLowerCase() throws Exception {
        WorkoutResponse benchPress = new WorkoutResponse(workouts.get(0));
        when(workoutService.findByExactWorkoutName("bench press")).thenReturn(ResponseEntity.ok(benchPress));
        mockMvc.perform(get(apiUrl + "/workout").param("name", "bench press"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workoutName", equalTo("Bench press")));
    }

    @Test
    void findByExactWorkoutNameRandomCase() throws Exception {
        WorkoutResponse benchPress = new WorkoutResponse(workouts.get(0));
        when(workoutService.findByExactWorkoutName("beNcH pRess")).thenReturn(ResponseEntity.ok(benchPress));
        mockMvc.perform(get(apiUrl + "/workout").param("name", "beNcH pRess"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workoutName", equalTo("Bench press")));
    }

    @Test
    void findByExactWorkoutNameNotFound() throws Exception {
        when(workoutService.findByExactWorkoutName("nothing")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(get(apiUrl + "/workout").param("name", "nothing"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findByBurnedCaloriesBetween() throws Exception {
        List<WorkoutResponse> foundWorkouts = new ArrayList<>();
        foundWorkouts.add(new WorkoutResponse(workouts.get(2))); //Overhead barbeel press
        foundWorkouts.add(new WorkoutResponse(workouts.get(3))); //Cable chest press

        when(workoutService.findByBurnedKiloCaloriesBetween(200, 400)).thenReturn(foundWorkouts);
        mockMvc.perform(get(apiUrl + "/calories").param("startkilocalories", String.valueOf(200))
                        .param("endkilocalories", String.valueOf(400)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].workoutName", equalTo("Overhead barbell press")))
                .andExpect(jsonPath("$[1].workoutName", equalTo("Cable chest press")));
    }

    @Test
    void findByBurnedCaloriesBetweenEmptyList() throws Exception {
        List<WorkoutResponse> foundWorkouts = new ArrayList<>();

        when(workoutService.findByBurnedKiloCaloriesBetween(100, 200)).thenReturn(foundWorkouts);
        mockMvc.perform(get(apiUrl + "/calories").param("startkilocalories", String.valueOf(100))
                        .param("endkilocalories", String.valueOf(200)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void deleteById() throws Exception {

        when(workoutService.deleteById("1")).thenReturn(ResponseEntity.ok("Workout 'Bench press' succesfully deleted."));
        mockMvc.perform(delete(apiUrl + "/workout").param("id", String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk());

        when(workoutService.deleteById("1")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(delete(apiUrl + "/workout").param("id", String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteByIdNotFound() throws Exception {

        when(workoutService.deleteById("100")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(delete(apiUrl + "/workout").param("id", String.valueOf(100)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void saveWorkout() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("test workout");
        testWorkout.setBurnedKiloCaloriesPHour(100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);


        URI location = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8080/recipes?id=5").build().toUri();

        when(workoutService.saveWorkout(any(WorkoutRequest.class))).thenReturn(ResponseEntity.created(location).build());
        mockMvc.perform(post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testWorkout)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("location", "http://localhost:8080/recipes?id=5"));
    }

    @Test
    void saveWorkoutEmptyNameValidationError() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("");
        testWorkout.setBurnedKiloCaloriesPHour(100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);

        when(workoutService.saveWorkout(any(WorkoutRequest.class))).thenReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testWorkout)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveWorkoutNullNameValidationError() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setBurnedKiloCaloriesPHour(100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);

        when(workoutService.saveWorkout(any(WorkoutRequest.class))).thenReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testWorkout)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveWorkoutNegativeBurnedKiloCalsValidationError() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("test workout");
        testWorkout.setBurnedKiloCaloriesPHour(-100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);


        when(workoutService.saveWorkout(any(WorkoutRequest.class))).thenReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testWorkout)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveWorkoutEmptyMuscleGroupIdsValidationError() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("");
        testWorkout.setBurnedKiloCaloriesPHour(100);

        when(workoutService.saveWorkout(any(WorkoutRequest.class))).thenReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testWorkout)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWorkout() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("test workout");
        testWorkout.setBurnedKiloCaloriesPHour(100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);


        when(workoutService.updateWorkout("1", testWorkout)).thenReturn(ResponseEntity.ok("Workout successfully updated."));
        mockMvc.perform(put(apiUrl + "/workout").param("id", String.valueOf(1))
                                .content(objectMapper.writeValueAsString(testWorkout))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void updateWorkoutNotFound() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("test workout");
        testWorkout.setBurnedKiloCaloriesPHour(100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);


        when(workoutService.updateWorkout("100", testWorkout)).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put(apiUrl + "/workout").param("id", String.valueOf(100))
                        .content(objectMapper.writeValueAsString(testWorkout))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void updateWorkoutEmptyWorkoutNameValidationError() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("");
        testWorkout.setBurnedKiloCaloriesPHour(100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);


        when(workoutService.updateWorkout("1", testWorkout)).thenReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(put(apiUrl + "/workout").param("id", String.valueOf("1"))
                        .content(objectMapper.writeValueAsString(testWorkout))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWorkoutNullWorkoutNameValidationError() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setBurnedKiloCaloriesPHour(100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);


        when(workoutService.updateWorkout("1", testWorkout)).thenReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(put(apiUrl + "/workout").param("id", String.valueOf("1"))
                        .content(objectMapper.writeValueAsString(testWorkout))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWorkoutNegativeBurnedKiloCalValidationError() throws Exception {
        WorkoutRequest testWorkout = new WorkoutRequest();
        testWorkout.setWorkoutName("test workout");
        testWorkout.setBurnedKiloCaloriesPHour(-100);
        List<String> muscleGroupIds = new ArrayList<>();
        muscleGroupIds.add("1");
        testWorkout.setMuscleGroupIds(muscleGroupIds);


        when(workoutService.updateWorkout("1", testWorkout)).thenReturn(ResponseEntity.badRequest().build());
        mockMvc.perform(put(apiUrl + "/workout").param("id", String.valueOf("1"))
                        .content(objectMapper.writeValueAsString(testWorkout))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeMuscleGroup() throws Exception {

        MuscleGroup muscleGroup = workoutMuscleGroups.get(1);
        when(workoutService.removeMuscleGroup("1", "2"))
                .thenReturn(ResponseEntity.ok("Muscle group '"+ muscleGroup.getMuscleGroupName() + "' succesfully removed from workout."));
        mockMvc.perform(put(apiUrl + "/musclegroup").param("workoutid", String.valueOf(1L)).param("musclegroupid", String.valueOf(2L))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void removeMuscleGroupButWorkoutNotFound() throws Exception {

        when(workoutService.removeMuscleGroup("200", "1"))
                .thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put(apiUrl + "/musclegroup").param("workoutid", String.valueOf(200)).param("musclegroupid", String.valueOf(2))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void removeMuscleGroupNotFound() throws Exception {

        when(workoutService.removeMuscleGroup("1", "5"))
                .thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put(apiUrl + "/musclegroup").param("workoutid", String.valueOf(1)).param("musclegroupid", String.valueOf(2))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}