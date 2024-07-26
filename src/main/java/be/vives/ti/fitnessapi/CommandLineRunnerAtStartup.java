package be.vives.ti.fitnessapi;

import be.vives.ti.fitnessapi.domain.*;
import be.vives.ti.fitnessapi.repository.MuscleGroupRepository;
import be.vives.ti.fitnessapi.repository.RecipeRepository;
import be.vives.ti.fitnessapi.repository.WorkoutRepository;
import org.hibernate.jdbc.Work;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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


        Workout barbellPress = new Workout("Barbell press", 216);
        Workout seatedOverheadPress = new Workout("Seated overhead press", 216);
        Workout machineLateralRaises = new Workout("Machine lateral raises", 272);
        Workout rowing = new Workout("Rowing", 840);
        Workout alternateDumbellCurls = new Workout("Alternate dumbell curls", 323);
        Workout olympicBarbellLift = new Workout("Olympic barbell lift", 643);
        Workout barbellRows = new Workout("Standing barbell rows", 213);
        Workout benchPress = new Workout("Barbell bench press", 459);
        Workout jogging = new Workout("Jogging", 603);
        Workout cycling = new Workout("Cycling", 450);
        Workout abdominalCrunches = new Workout("Abdominal crunches", 396);


        RecipeIngredient chickenThighs = new RecipeIngredient("skinless boneless chicken thighs, cut in half", "3 pieces");
        RecipeIngredient rapeseedOil = new RecipeIngredient("rapeseed oil", "2 tbsp");
        RecipeIngredient garlicCloves = new RecipeIngredient("garlic cloves, bashed", "2 pieces");
        RecipeIngredient coriander = new RecipeIngredient("small pack coriander", "½");
        RecipeIngredient parsley = new RecipeIngredient("small pack parsley", "½");
        RecipeIngredient anchovy = new RecipeIngredient("anchovy fillet", "½");
        RecipeIngredient capers = new RecipeIngredient("capers", "1 piece");
        RecipeIngredient rice = new RecipeIngredient("pouch cooked wholegrain rice", "200g");
        RecipeIngredient spinach = new RecipeIngredient("baby leaf spinach", "200g");

        deltoids.addWorkout(barbellPress);
        barbellPress.addMuscleGroup(deltoids);

        deltoids.addWorkout(seatedOverheadPress);
        seatedOverheadPress.addMuscleGroup(deltoids);

        deltoids.addWorkout(machineLateralRaises);
        machineLateralRaises.addMuscleGroup(deltoids);

        deltoids.addWorkout(benchPress);
        benchPress.addMuscleGroup(deltoids);

        triceps.addWorkout(barbellPress);
        barbellPress.addMuscleGroup(triceps);

        triceps.addWorkout(seatedOverheadPress);
        seatedOverheadPress.addMuscleGroup(triceps);

        triceps.addWorkout(machineLateralRaises);
        machineLateralRaises.addMuscleGroup(triceps);

        triceps.addWorkout(benchPress);
        benchPress.addMuscleGroup(triceps);

        biceps.addWorkout(rowing);
        rowing.addMuscleGroup(biceps);

        biceps.addWorkout(alternateDumbellCurls);
        alternateDumbellCurls.addMuscleGroup(biceps);

        biceps.addWorkout(barbellRows);
        barbellRows.addMuscleGroup(biceps);

        wristFlexors.addWorkout(rowing);
        rowing.addMuscleGroup(wristFlexors);

        wristFlexors.addWorkout(alternateDumbellCurls);
        alternateDumbellCurls.addMuscleGroup(wristFlexors);

        wristFlexors.addWorkout(barbellRows);
        barbellRows.addMuscleGroup(wristFlexors);

        wristFlexors.addWorkout(barbellPress);
        barbellPress.addMuscleGroup(wristFlexors);

        wristFlexors.addWorkout(benchPress);
        benchPress.addMuscleGroup(wristFlexors);

        lats.addWorkout(rowing);
        rowing.addMuscleGroup(lats);

        abdominals.addWorkout(abdominalCrunches);
        abdominalCrunches.addMuscleGroup(abdominals);

        trapezius.addWorkout(barbellRows);
        barbellRows.addMuscleGroup(trapezius);

        quadriceps.addWorkout(rowing);
        rowing.addMuscleGroup(quadriceps);

        quadriceps.addWorkout(olympicBarbellLift);
        olympicBarbellLift.addMuscleGroup(quadriceps);

        quadriceps.addWorkout(cycling);
        cycling.addMuscleGroup(quadriceps);

        hamstrings.addWorkout(cycling);
        cycling.addMuscleGroup(hamstrings);

        calves.addWorkout(jogging);
        jogging.addMuscleGroup(calves);

        calves.addWorkout(cycling);
        cycling.addMuscleGroup(calves);

        heart.addWorkout(jogging);
        jogging.addMuscleGroup(heart);

        heart.addWorkout(rowing);
        rowing.addMuscleGroup(heart);

        heart.addWorkout(cycling);
        cycling.addMuscleGroup(heart);

        List<RecipeIngredient> roastChickenIngredients = new ArrayList<>();
        roastChickenIngredients.add(chickenThighs);
        roastChickenIngredients.add(rapeseedOil);
        roastChickenIngredients.add(garlicCloves);
        roastChickenIngredients.add(coriander);
        roastChickenIngredients.add(parsley);
        roastChickenIngredients.add(anchovy);
        roastChickenIngredients.add(capers);
        roastChickenIngredients.add(rice);
        roastChickenIngredients.add(spinach);

        RecipeInstruction roastChickenStep1 = new RecipeInstruction("Heat oven to 200C/180C fan/gas 6. Season the chicken, rub with ½ tbsp oil, then put in a large roasting tin with the garlic and roast for 25-30 mins.",1);
        RecipeInstruction roastChickenStep2 = new RecipeInstruction("Meanwhile, blitz the herbs, anchovy, capers, lemon juice and remaining oil with some seasoning in a food processor until finely chopped. Set aside.",2);
        RecipeInstruction roastChickenStep3 = new RecipeInstruction("Once the chicken is cooked, remove the tin from the oven and squeeze the garlic out of their skins. Tip in the rice and use a wooden spoon to break it up, then add the spinach and lemon zest and toss. Return to the oven for 5 mins. Divide between bowls and dollop on the salsa verde.",3);

        List<RecipeInstruction> roastChickenInstructions = new ArrayList<>();
        roastChickenInstructions.add(roastChickenStep1);
        roastChickenInstructions.add(roastChickenStep2);
        roastChickenInstructions.add(roastChickenStep3);

        Recipe roastChickenThighs = new Recipe("Roast chicken thighs with brown rice & salsa verde", roastChickenInstructions, roastChickenIngredients, 423);

        recipeRepository.save(roastChickenThighs);


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
