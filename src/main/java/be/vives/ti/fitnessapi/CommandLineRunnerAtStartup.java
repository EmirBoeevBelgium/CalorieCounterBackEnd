package be.vives.ti.fitnessapi;

import be.vives.ti.fitnessapi.domain.MuscleGroup;
import be.vives.ti.fitnessapi.domain.Recipe;
import be.vives.ti.fitnessapi.domain.Workout;
import be.vives.ti.fitnessapi.repository.MuscleGroupRepository;
import be.vives.ti.fitnessapi.repository.RecipeRepository;
import be.vives.ti.fitnessapi.repository.WorkoutRepository;
import org.hibernate.jdbc.Work;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommandLineRunnerAtStartup implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    private final WorkoutRepository workoutRepository;

    private final MuscleGroupRepository muscleGroupRepository;

    public CommandLineRunnerAtStartup(RecipeRepository recipeRepository, WorkoutRepository workoutRepository, MuscleGroupRepository muscleGroupRepository) {
        this.recipeRepository = recipeRepository;
        this.workoutRepository = workoutRepository;
        this.muscleGroupRepository = muscleGroupRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        MuscleGroup deltoids = new MuscleGroup("Deltoids", "Your deltoid muscles work alongside your other shoulder muscles, such as the rotator cuff muscles, to help you perform a variety of movements. Deltoid muscle functions include:\n" +
                "\n" +
                "- Arm abduction, which means raising your arm out to the side of your body.\n" +
                "- Compensation for lost arm strength if you have an injury, such as a rotator cuff tear.\n" +
                "- Flexion (moving your arm forward, toward an overhead position) and extension (moving your arm backward, behind your body).\n" +
                "- Stabilization of your shoulder joint to prevent dislocations as you lift your arm or while you carry weight with your arms at your side."
                );

        MuscleGroup biceps = new MuscleGroup("Biceps", "The biceps is one of four muscles alongside the brachialis, brachioradialis, and coracobrachialis muscles that make up the upper arm.\n" +
                "The biceps muscle is comprised of two heads. At each end are connective tissues called tendons that anchor the muscles to bone.\n" +
                "Despite what some think, the biceps is not the most powerful flexor of the forearm. Although the biceps is the most prominent muscle of the upper arm, it serves to support and stabilize the deeper (and stronger) brachialis muscle whenever lifting or lowering the forearm."
        );

        MuscleGroup triceps = new MuscleGroup("Triceps", "The triceps brachii is a large, thick muscle on the dorsal part of the upper arm. It often appears as the shape of a horseshoe on the posterior aspect of the arm. The main function of the triceps is the extension of the elbow joint.[1]\n" +
                "\n" +
                "The Triceps brachii gets its name with tri referring to \"three\" muscle heads or points of origin (with Brachii referring to the arm). These include the: Medial head; Lateral head; Long head");

        MuscleGroup pectorals = new MuscleGroup("Pectorals", "The pectoralis major (from Latin pectus 'breast') is a thick, fan-shaped or triangular convergent muscle of the human chest. It makes up the bulk of the chest muscles and lies under the breast. Beneath the pectoralis major is the pectoralis minor muscle.\n" +
                "\n" +
                "The pectoralis major arises from parts of the clavicle and sternum, costal cartilages of the true ribs, and the aponeurosis of the abdominal external oblique muscle; it inserts onto the lateral lip of the bicipital groove. It receives double motor innervation from the medial pectoral nerve and the lateral pectoral nerve. The pectoralis major's primary functions are flexion, adduction, and internal rotation of the humerus. The pectoral major may colloquially be referred to as \"pecs\", \"pectoral muscle\", or \"chest muscle\", because it is the largest and most superficial muscle in the chest area.");

        MuscleGroup trapezius = new MuscleGroup("Trapezius", "The trapezius[4] is a large paired trapezoid-shaped surface muscle that extends longitudinally from the occipital bone to the lower thoracic vertebrae of the spine and laterally to the spine of the scapula. It moves the scapula and supports the arm.\n" +
                "\n" +
                "The trapezius has three functional parts: an upper (descending) part which supports the weight of the arm; a middle region (transverse), which retracts the scapula; and a lower (ascending) part which medially rotates and depresses the scapula.");

        MuscleGroup abdominals = new MuscleGroup("Abdominals", "The abdominal muscles are located between the ribs and the pelvis on the front of the body. The abdominal muscles support the trunk, allow movement and hold organs in place by regulating internal abdominal pressure.");

        MuscleGroup wristFlexors = new MuscleGroup("Wrist flexors", "This muscle group is associated with pronation of the forearm, flexion of the wrist and flexion of the fingers.\n" +
                "\n" +
                "They are mostly innervated by the median nerve (except for the flexor carpi ulnaris and medial half of flexor digitorum profundus, which are innervated by the ulnar nerve), and they receive arterial supply from the ulnar artery and radial artery.");

        MuscleGroup quadriceps = new MuscleGroup("Quadriceps", "The quadriceps are a group of muscles present on the front of the thigh. They consist of four distinct muscles: the rectus femoris, the vastus lateralis, the vastus intermedius, and the vastus medialis. They are responsible for extending the leg and helping with movements such as walking and jumping.");

        MuscleGroup lats = new MuscleGroup("Lats","The latissimus dorsi muscle is a broad, flat muscle that occupies the majority of the lower posterior thorax. The muscle's primary function is of the upper extremity but is also considered to be a respiratory accessory muscle.");

        MuscleGroup hamstrings = new MuscleGroup("Hamstrings", "In human anatomy, a hamstring (/ˈhæmstrɪŋ/) is any one of the three posterior thigh muscles between the hip and the knee (from medial to lateral: semimembranosus, semitendinosus and biceps femoris).");

        MuscleGroup calves = new MuscleGroup("Calves", "Your calf muscle sits in the back of your lower leg. It starts below your knee and extends to your ankle. It allows you to walk, run, jump and flex your foot. It also helps you stand up straight. Strains and cramps are the most common conditions that affect your calf muscle.");
        MuscleGroup heart = new MuscleGroup("Heart", "Cardiac muscle (also called heart muscle or myocardium) is one of three types of vertebrate muscle tissues, with the other two being skeletal muscle and smooth muscle. It is an involuntary, striated muscle that constitutes the main tissue of the wall of the heart. The cardiac muscle (myocardium) forms a thick middle layer between the outer layer of the heart wall (the pericardium) and the inner layer (the endocardium), with blood supplied via the coronary circulation. It is composed of individual cardiac muscle cells joined by intercalated discs, and encased by collagen fibers and other substances that form the extracellular matrix.");


        Workout barbellPress = new Workout("Barbell press", 216, 4);
        Workout seatedOverheadPress = new Workout("Seated overhead press", 216, 4);
        Workout machineLateralRaises = new Workout("Machine lateral raises", 272, 4.5);
        Workout rowing = new Workout("Rowing", 840, 14);
        Workout alternateDumbellCurls = new Workout("Alternate dumbell curls", 323, 5.4);
        Workout olympicBarbellLift = new Workout("Olympic barbell lift", 643, 11);
        Workout barbellRows = new Workout("Standing barbell rows", 213, 4.3);
        Workout benchPress = new Workout("Barbell bench press", 459, 7.7);
        Workout jogging = new Workout("Jogging", 603, 10.1);
        Workout cycling = new Workout("Cycling", 450, 7.5);
        Workout abdominalCrunches = new Workout("Abdominal crunches", 396, 6.6);


        Recipe roastChickenThighs = new Recipe();

        workoutRepository.save(barbellPress);
        workoutRepository.save(seatedOverheadPress);
        workoutRepository.save(machineLateralRaises);
        workoutRepository.save(rowing);
        workoutRepository.save(alternateDumbellCurls);
        workoutRepository.save(olympicBarbellLift);
        workoutRepository.save(barbellRows);
        workoutRepository.save(benchPress);
        workoutRepository.save(jogging);
        workoutRepository.save(cycling);
        workoutRepository.save(abdominalCrunches);


        deltoids.addWorkout(barbellPress);
        deltoids.addWorkout(seatedOverheadPress);
        deltoids.addWorkout(machineLateralRaises);
        deltoids.addWorkout(benchPress);

        triceps.addWorkout(barbellPress);
        triceps.addWorkout(seatedOverheadPress);
        triceps.addWorkout(machineLateralRaises);
        triceps.addWorkout(benchPress);

        biceps.addWorkout(rowing);
        biceps.addWorkout(alternateDumbellCurls);
        biceps.addWorkout(barbellRows);

        wristFlexors.addWorkout(rowing);
        wristFlexors.addWorkout(alternateDumbellCurls);
        wristFlexors.addWorkout(barbellRows);
        wristFlexors.addWorkout(barbellPress);
        wristFlexors.addWorkout(benchPress);

        lats.addWorkout(rowing);
        abdominals.addWorkout(abdominalCrunches);
        trapezius.addWorkout(barbellRows);
        quadriceps.addWorkout(rowing);
        quadriceps.addWorkout(olympicBarbellLift);
        quadriceps.addWorkout(cycling);
        hamstrings.addWorkout(cycling);

        calves.addWorkout(jogging);
        calves.addWorkout(cycling);

        heart.addWorkout(jogging);
        heart.addWorkout(rowing);
        heart.addWorkout(cycling);

        muscleGroupRepository.save(deltoids);
        muscleGroupRepository.save(biceps);
        muscleGroupRepository.save(triceps);
        muscleGroupRepository.save(trapezius);
        muscleGroupRepository.save(pectorals);
        muscleGroupRepository.save(abdominals);
        muscleGroupRepository.save(wristFlexors);
        muscleGroupRepository.save(quadriceps);
        muscleGroupRepository.save(lats);
        muscleGroupRepository.save(hamstrings);
        muscleGroupRepository.save(calves);
        muscleGroupRepository.save(heart);


    }

}
