package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeightTrainingExerciseTest {

    private WeightTrainingExercise squat;
    private WeightTrainingExercise legPress;
    private WeightTrainingExercise lunge;
    private WeightTrainingExercise deadLift;
    private WeightTrainingExercise bench;
    private WeightTrainingExercise shoulderPress;
    private WeightTrainingExercise dumbbells;

    @BeforeEach
    public void setUp() {
        squat = new WeightTrainingExercise("squat", 2, 8);
    }

    @Test
    public void testConstructor() {
        legPress = new WeightTrainingExercise("legpress", 1, 5);
        lunge = new WeightTrainingExercise("lunge", 4, 8);
        deadLift = new WeightTrainingExercise("deadlift", 2, 2);
        bench = new WeightTrainingExercise("bench", 3, 6);
        shoulderPress = new WeightTrainingExercise("shoulderpress", 3, 15);
        dumbbells = new WeightTrainingExercise("dumbbells", 5, 0);

        assertEquals(squat.calsBurned, 32);
        assertEquals(legPress.calsBurned, 5);
        assertEquals(lunge.calsBurned, 64);
        assertEquals(deadLift.calsBurned, 4);
        assertEquals(bench.calsBurned, 18);
        assertEquals(shoulderPress.calsBurned, 90);
        assertEquals(dumbbells.calsBurned, 0);
    }

    @Test
    public void testGetStats() {
        assertEquals(squat.getStats(), "2x8 reps");
    }

    @Test
    void testGetSets() {
        assertEquals(squat.getSets(), 2);
    }

    @Test
    void testSetSets() {
        squat.setSets(4);
        assertEquals(squat.getSets(), 4);
    }

    @Test
    void testGetReps() {
        assertEquals(squat.getReps(), 8);
    }

    @Test
    void testSetReps() {
        squat.setReps(6);
        assertEquals(squat.getReps(), 6);
    }
}