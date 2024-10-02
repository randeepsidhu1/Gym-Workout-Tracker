package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardioExerciseTest {

    private CardioExercise running;
    private CardioExercise biking;
    private CardioExercise walking;
    private CardioExercise swimming;

    @BeforeEach
    public void setUp() {
        running = new CardioExercise("running", 30, 5.0);
    }

    @Test
    public void testConstructor() {
        biking = new CardioExercise("biking", 120, 20.0);
        walking = new CardioExercise("walking", 30, 2.0);
        swimming = new CardioExercise("swimming", 40, 2.0);

        assertEquals(running.calsBurned, 360);
        assertEquals(biking.calsBurned, 720);
        assertEquals(walking.calsBurned, 120);
        assertEquals(swimming.calsBurned, 400);
    }

    @Test
    public void testGetStats() {
        assertEquals(running.getStats(), "30 mins and 5.0KM");
    }

    @Test
    public void testGetDuration() {
        assertEquals(running.getDuration(), 30);
    }

    @Test
    public void testSetDuration() {
        running.setDuration(45);
        assertEquals(running.getDuration(), 45);
    }

    @Test
    public void testGetDistance() {
        assertEquals(running.getDistance(), 5.0);
    }

    @Test
    public void testSetDistance() {
        running.setDistance(7.5);
        assertEquals(running.getDistance(), 7.5);
    }
}