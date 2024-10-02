package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BodyWeightExerciseTest {

    private BodyWeightExercise pushUps;
    private BodyWeightExercise pullUps;
    private BodyWeightExercise crunches;
    private BodyWeightExercise jumpingJacks;
    private BodyWeightExercise sitUps;

    @BeforeEach
    public void setUp() {
        pushUps = new BodyWeightExercise("pushups", 3, 10);
    }

    @Test
    public void testConstructor() {
        pullUps = new BodyWeightExercise("pullups", 1, 20);
        crunches = new BodyWeightExercise("crunches", 2, 25);
        jumpingJacks = new BodyWeightExercise("jumpingjacks", 3, 30);
        sitUps = new BodyWeightExercise("situps", 0, 8);

        assertEquals(pushUps.calsBurned, 9);
        assertEquals(pullUps.calsBurned, 20);
        assertEquals(crunches.calsBurned, 35);
        assertEquals(jumpingJacks.calsBurned, 90);
        assertEquals(sitUps.calsBurned, 0);
    }

    @Test
    public void testGetStats() {
        assertEquals(pushUps.getStats(), "3x10 reps");
    }

    @Test
    void testGetSets() {
        assertEquals(pushUps.getSets(), 3);
    }

    @Test
    void testSetSets() {
        pushUps.setSets(5);
        assertEquals(pushUps.getSets(), 5);
    }

    @Test
    void testGetReps() {
        assertEquals(pushUps.getReps(), 10);
    }

    @Test
    void testSetReps() {
        pushUps.setReps(12);
        assertEquals(pushUps.getReps(), 12);
    }
}