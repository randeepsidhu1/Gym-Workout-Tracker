import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Exercise;
import model.Workout;

public class WorkoutTest {

    private Workout testWorkout;
    private Exercise testExercise1;
    private Exercise testExercise2;

    @BeforeEach
    void runBefore() {
        testWorkout = new Workout();
        testExercise1 = new Exercise("Push-ups", 3, 15, 0);
        testExercise2 = new Exercise("Squats", 4, 12, 50);
    }

    @Test
    void testAddExercise() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);

        assertEquals(2, testWorkout.getExercises().size());
        assertTrue(testWorkout.getExercises().contains(testExercise1));
        assertTrue(testWorkout.getExercises().contains(testExercise2));
    }

    @Test
    void testRemoveExercise() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);

        testWorkout.removeExercise(testExercise1);

        assertEquals(1, testWorkout.getExercises().size());
        assertTrue(testWorkout.getExercises().contains(testExercise2));
        assertTrue(!testWorkout.getExercises().contains(testExercise1));

    }

    @Test
    public void testGetExercises() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);

        List<Exercise> storedExercises = testWorkout.getExercises();
        assertEquals(2, storedExercises.size());
        assertTrue(storedExercises.contains(testExercise1));
        assertTrue(storedExercises.contains(testExercise2));
    }

    @Test
    void testGetDateTimeCreated() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime workoutCreationTime = testWorkout.getDateTimeCreated();

        assertTrue(now.minusSeconds(5).isBefore(workoutCreationTime) || now.isEqual(workoutCreationTime));
        assertTrue(now.plusSeconds(5).isAfter(workoutCreationTime) || now.isEqual(workoutCreationTime));
    }
}
