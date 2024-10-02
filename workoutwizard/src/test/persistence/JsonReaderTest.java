package persistence;

import model.UserWorkouts;
import model.WorkoutRoutine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            UserWorkouts userWorkouts = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadWorkouts() {
        JsonReader reader = new JsonReader("./data/testJSON.json");
        try {
            UserWorkouts userWorkouts = reader.read();
            assertEquals(1, userWorkouts.getWorkouts().size());
            WorkoutRoutine workoutRoutine = userWorkouts.getWorkouts().get(0);
            assertEquals(4, workoutRoutine.getExercises().size());
            assertEquals(LocalDate.parse("2024-03-06"), workoutRoutine.getDate());
            checkExercise("biking", workoutRoutine.getExercises().get(0));
            checkExercise("deadlift", workoutRoutine.getExercises().get(1));
            checkExercise("crunches", workoutRoutine.getExercises().get(2));
            checkExercise("soccer", workoutRoutine.getExercises().get(3));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}