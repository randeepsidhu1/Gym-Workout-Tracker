package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    private Exercise running;
    private Exercise pushUps;

    @BeforeEach
    public void setUp() {
        running = new CardioExercise("running", 10, 2.0);
        pushUps = new BodyWeightExercise("pushups", 5, 15);
    }

    @Test
    public void testGetStats() {
        assertEquals(running.getStats(), "10 mins and 2.0KM");
        assertEquals(pushUps.getStats(), "5x15 reps");
    }

    @Test
    public void testGetName() {
        assertEquals(running.getName(), "running");
    }

    @Test
    public void testGetCalsBurned() {
        assertEquals(running.getCalsBurned(), 120);
    }

    @Test
    public void testGetDistanceTravelled() {
        assertEquals(running.getDistanceTravelled(), 2.0);
    }

}