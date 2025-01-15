import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.BestExerciseLog;
import model.Exercise;

public class BestExerciseLogTest {

    private BestExerciseLog exerciseLog;
    private Exercise exercise1;
    private Exercise exercise2;

    @BeforeEach
    public void setUp() {
        exerciseLog = new BestExerciseLog();
        exercise1 = new Exercise("Bench Press", 3, 10, 100);
        exercise2 = new Exercise("Deadlifts", 4, 8, 150);
    }

    @Test
    public void testAddPrExercise() {
        exerciseLog.addPrExercise(exercise1);
        List<Exercise> storedExercises = exerciseLog.getAllExercises();
        assertEquals(1, storedExercises.size());
        assertEquals(exercise1, storedExercises.get(0));

        exerciseLog.addPrExercise(new Exercise("Bench Press", 3, 10, 110));
        assertEquals(1, storedExercises.size());
        assertEquals(110, storedExercises.get(0).getWeight());

        exerciseLog.addPrExercise(new Exercise("Bench Press", 3, 10, 90));
        assertEquals(1, storedExercises.size());
        assertEquals(110, storedExercises.get(0).getWeight());
    }

    @Test
    public void testGetAllExercises() {
        exerciseLog.addPrExercise(exercise1);
        exerciseLog.addPrExercise(exercise2);

        List<Exercise> storedExercises = exerciseLog.getAllExercises();
        assertEquals(2, storedExercises.size());
        assertTrue(storedExercises.contains(exercise1));
        assertTrue(storedExercises.contains(exercise2));
    }

    @Test
    public void testGetBestWeight() {
        exerciseLog.addPrExercise(exercise1);
        exerciseLog.addPrExercise(new Exercise("Bench Press", 3, 10, 110));
        assertEquals(110, exerciseLog.getBestWeight("Bench Press"));

        exerciseLog.addPrExercise(exercise2);
        Integer bestWeight = exerciseLog.getBestWeight("Squats");
        assertNull(bestWeight);

    }

}
