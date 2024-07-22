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
        muscleGroups.add(new MuscleGroup("Biceps", "Double muscle in your arms."));
        muscleGroups.add(new MuscleGroup("Triceps", "Triple muscle in your arms."));
        muscleGroups.add(new MuscleGroup("Pectorals", "Chest muscles."));



        muscleGroupWorkouts = new ArrayList<>();
        muscleGroupWorkouts.add(new Workout("Bench press", 320));
        muscleGroupWorkouts.add(new Workout("Preacher curls", 218));
        muscleGroupWorkouts.add(new Workout("Overhead barbell press", 291));
        muscleGroupWorkouts.add(new Workout("Cable chest press", 120));
        muscleGroupWorkouts.add(new Workout("Downward cable press", 244));

        MuscleGroup biceps = muscleGroups.get(0);
        MuscleGroup triceps = muscleGroups.get(1);
        MuscleGroup pecs = muscleGroups.get(2);

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
    void findByMuscleGroupNameUpperCase() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));
        when(muscleGroupService.findByMuscleGroupName("BICEPS")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "BICEPS"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.muscleGroupName", equalTo("Biceps")));
    }

    @Test
    void findByMuscleGroupNameLowerCase() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));
        when(muscleGroupService.findByMuscleGroupName("biceps")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "biceps"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.muscleGroupName", equalTo("Biceps")));
    }

    @Test
    void findByMuscleGroupNameRandomCase() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));
        when(muscleGroupService.findByMuscleGroupName("bIcEps")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "bIcEps"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.muscleGroupName", equalTo("Biceps")));
    }



    @Test
    void removeWorkoutFromMuscleGroup() throws Exception {
        MuscleGroupResponse biceps = new MuscleGroupResponse(muscleGroups.get(0));

        when(muscleGroupService.findByMuscleGroupName("bIcEps")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "bIcEps"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workouts", hasSize(1)));

        when(muscleGroupService.removeWorkout(1L, 1L)).thenReturn(ResponseEntity.ok("Workout 'Preacher curls' succesfully removed from muscle group."));
        mockMvc.perform(put(apiUrl + "/workout").param("musclegroupid", String.valueOf(1L)).param("workoutid", String.valueOf(1L)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        biceps.getWorkouts().clear();

        when(muscleGroupService.findByMuscleGroupName("bIcEps")).thenReturn(ResponseEntity.ok(biceps));
        mockMvc.perform(get(apiUrl + "/musclegroup").param("name", "bIcEps"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workouts", hasSize(0)));
    }
}