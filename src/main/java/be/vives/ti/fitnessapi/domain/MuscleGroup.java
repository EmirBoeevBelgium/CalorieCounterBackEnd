package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.jdbc.Work;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MUSCLEGROUP")
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String muscleGroupName;

    @NotNull
    private String muscleGroupDescription;


    @ManyToMany
    @JoinTable(
            name = "musclegroup_workout",
            joinColumns = @JoinColumn(name = "musclegroup_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    private List<Workout> musclegroupWorkouts = new ArrayList<>();
}
