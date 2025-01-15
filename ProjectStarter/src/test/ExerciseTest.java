
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Exercise;

public class ExerciseTest {

    private Exercise testExercise;

    @BeforeEach
    void runBefore() {
        testExercise = new Exercise("Bench Press", 3, 10, 100);

    }

    @Test
    void testGetName() {
        assertEquals("Bench Press", testExercise.getName());
    }

    @Test
    void testGetSets() {
        assertEquals(3, testExercise.getSets());
    }

    @Test
    void testGetReps() {
        assertEquals(10, testExercise.getReps());
    }

    @Test
    void testGetWeight() {
        assertEquals(100, testExercise.getWeight());
    }

    @Test
    void testSetSets() {
        testExercise.setSets(4);
        assertEquals(4, testExercise.getSets());
    }

    @Test
    void testSetReps() {
        testExercise.setReps(12);
        assertEquals(12, testExercise.getReps());
    }

    @Test
    void testSetWeight() {
        testExercise.setWeight(120);
        assertEquals(120, testExercise.getWeight());
    }
}
