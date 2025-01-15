import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Goal;

public class GoalTest {
    private Goal testGoal;

    @BeforeEach
    void runBefore() {
        testGoal = new Goal("Squats", 100);
    }

    @Test
    void testGetExerciseName() {
        assertEquals("Squats", testGoal.getExerciseName());
    }

    @Test
    void testGetTargetWeight() {
        assertEquals(100, testGoal.getTargetWeight());
    }

    @Test
    void testSetExerciseName() {
        testGoal.setExerciseName("Bench Press");
        assertEquals("Bench Press", testGoal.getExerciseName());
    }

    @Test
    void testSetTargetWeight() {
        testGoal.setTargetWeight(120);
        assertEquals(120, testGoal.getTargetWeight());

    }
}
