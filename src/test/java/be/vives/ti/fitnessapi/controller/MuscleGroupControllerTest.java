package be.vives.ti.fitnessapi.controller;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.response.MuscleGroupResponse;
import be.vives.ti.fitnessapi.service.MuscleGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MuscleGroupController.class)
class MuscleGroupControllerTest {

    private final String apiUrl = "/musclegroups";

    @MockBean
    private MuscleGroupService muscleGroupService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<MuscleGroup> muscleGroups;
    private List<Workout> muscleGroupWorkouts;

    @BeforeEach
    void setUp() {
        muscleGroups = new ArrayList<>();
        MuscleGroup biceps = new MuscleGroup("Biceps", "Double muscle in your arms.");
        biceps.setId("1");
        muscleGroups.add(biceps);
        MuscleGroup triceps = new MuscleGroup("Triceps", "Triple muscle in your arms.");
        triceps.setId("2");
        muscleGroups.add(triceps);
        MuscleGroup pecs = new MuscleGroup("Pectorals", "Chest muscles.");
        pecs.setId("3");
        muscleGroups.add(pecs);



        muscleGroupWorkouts = new ArrayList<>();
        muscleGroupWorkouts.add(new Workout("Bench press", 320));
        muscleGroupWorkouts.add(new Workout("Preacher curls", 218));
        muscleGroupWorkouts.add(new Workout("Overhead barbell press", 291));
        muscleGroupWorkouts.add(new Workout("Cable chest press", 120));
        muscleGroupWorkouts.add(new Workout("Downward cable press", 244));

        biceps = muscleGroups.get(0);
        triceps = muscleGroups.get(1);
        pecs = muscleGroups.get(2);

        Workout benchPress = muscleGroupWorkouts.get(0);
        Workout preacherCurls = muscleGroupWorkouts.get(1);
        Workout barbellPress = muscleGroupWorkouts.get(2);
        Workout cableChestPress = muscleGroupWorkouts.get(3);
        Workout downwardCablePress = muscleGroupWorkouts.get(4);

        pecs.addWorkout(benchPress);
        biceps.addWorkout(preacherCurls);
        triceps.addWorkout(barbellPress);
        pecs.addWorkout(cableChestPress);
        triceps.addWorkout(downwardCablePress);
        when(muscleGroupService.findAll()).thenReturn(muscleGroups.stream().map(MuscleGroupResponse::new).collect(Collectors.toList()));
    }

    @Test
    void findAllMuscleGroups() throws Exception{
        when(muscleGroupService.findAll()).thenReturn(muscleGroups.stream().map(MuscleGroupResponse::new).collect(Collectors.toList()));
        mockMvc.perform(get(apiUrl))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].muscleGroupName", equalTo("Biceps")))
                .andExpect(jsonPath("$[1].muscleGroupName", equalTo("Triceps")))
                .andExpect(jsonPath("$[2].muscleGroupName", equalTo("Pectorals")));
    }

    @Test
    void findById() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));

        String id = biceps.getId();
        when(muscleGroupService.findById(biceps.getId())).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/id").param("id", biceps.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.muscleGroupName", equalTo("Biceps")));
    }

    @Test
    void findByIdNotFound() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));

        String id = biceps.getId();
        when(muscleGroupService.findById("44")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(get(apiUrl + "/id").param("id", "44"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findByExactMuscleGroupNameUpperCase() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));
        when(muscleGroupService.findByExactMuscleGroupName("BICEPS")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "BICEPS"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.muscleGroupName", equalTo("Biceps")));
    }

    @Test
    void findByExactMuscleGroupNameLowerCase() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));
        when(muscleGroupService.findByExactMuscleGroupName("biceps")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "biceps"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.muscleGroupName", equalTo("Biceps")));
    }

    @Test
    void findByExactMuscleGroupNameRandomCase() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));
        when(muscleGroupService.findByExactMuscleGroupName("bIcEps")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "bIcEps"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.muscleGroupName", equalTo("Biceps")));
    }

    @Test
    void findByExactMuscleGroupNameNotFound() throws Exception {
        when(muscleGroupService.findByExactMuscleGroupName("quadriceps")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "quadriceps"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void removeWorkoutFromMuscleGroup() throws Exception {


        when(muscleGroupService.removeWorkout("1", "1")).thenReturn(ResponseEntity.ok("Workout 'Preacher curls' succesfully removed from muscle group."));
        mockMvc.perform(put(apiUrl + "/workout").param("musclegroupid", String.valueOf(1)).param("workoutid", String.valueOf(1)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        when(muscleGroupService.removeWorkout("1", "1")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put(apiUrl + "/workout").param("musclegroupid", String.valueOf(1)).param("workoutid", String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void removeWorkoutFromMuscleGroupButWorkoutNotFound() throws Exception {

        when(muscleGroupService.removeWorkout("1", "2")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put(apiUrl + "/workout").param("musclegroupid", String.valueOf(1)).param("workoutid", String.valueOf(2)))
                .andDo(print())
                .andExpect(status().isNotFound());


    }

    @Test
    void removeWorkoutFromMuscleGroupButMuscleGroupNotFound() throws Exception {

        when(muscleGroupService.removeWorkout("100", "1")).thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(put(apiUrl + "/workout").param("musclegroupid", String.valueOf("100")).param("workoutid", String.valueOf("1")))
                .andDo(print())
                .andExpect(status().isNotFound());


    }
}