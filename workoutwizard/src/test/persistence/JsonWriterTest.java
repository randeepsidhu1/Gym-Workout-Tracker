package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class JsonWriterTest extends JsonTest {
    private UserWorkouts userWorkouts;
    private CardioExercise biking;
    private WeightTrainingExercise deadlift;
    private BodyWeightExercise crunches;
    private SportExercise soccer;
    private WorkoutRoutine workoutRoutine;

    @BeforeEach
    void setUp() {
        userWorkouts = new UserWorkouts();
        biking = new CardioExercise("biking", 45, 2.34);
        deadlift = new WeightTrainingExercise("deadlift", 4, 4);
        crunches = new BodyWeightExercise("crunches", 6, 32);
        soccer = new SportExercise("soccer", 90, "average");
        workoutRoutine = new WorkoutRoutine(LocalDate.parse("2024-03-06"));
        workoutRoutine.addExercise(biking);
        workoutRoutine.addExercise(deadlift);
        workoutRoutine.addExercise(crunches);
        workoutRoutine.addExercise(soccer);
        workoutRoutine.setCompleted(true);
        userWorkouts.addWorkoutRoutine(workoutRoutine);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.openFile();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteWorkouts() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriteWorkout.json");
            writer.openFile();
            writer.write(userWorkouts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteWorkout.json");
            userWorkouts = reader.read();
            assertEquals(1, userWorkouts.getWorkouts().size());
            workoutRoutine = userWorkouts.getWorkouts().get(0);
            assertEquals(LocalDate.parse("2024-03-06"), workoutRoutine.getDate());
            assertEquals(true, workoutRoutine.isCompleted());
            assertEquals(4, workoutRoutine.getExercises().size());
            checkExercise("biking", workoutRoutine.getExercises().get(0));
            checkExercise("deadlift", workoutRoutine.getExercises().get(1));
            checkExercise("crunches", workoutRoutine.getExercises().get(2));
            checkExercise("soccer", workoutRoutine.getExercises().get(3));
        } catch (IOException e) {
            fail("Unexpected exception");
        }
    }
}