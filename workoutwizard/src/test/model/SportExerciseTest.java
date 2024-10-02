package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SportExerciseTest {

    private SportExercise basketball;
    private SportExercise soccer;
    private SportExercise hockey;
    private SportExercise baseball;

    @BeforeEach
    public void setUp() {
        basketball = new SportExercise("basketball", 45, "average");
    }

    @Test
    public void testConstructor() {
        soccer = new SportExercise("soccer", 90, "light");
        hockey = new SportExercise("hockey", 20, "intense");
        baseball = new SportExercise("baseball", 140, "light");

        assertEquals(basketball.calsBurned, 630);
        assertEquals(soccer.calsBurned, 450);
        assertEquals(hockey.calsBurned, 600);
        assertEquals(baseball.calsBurned, 280);
    }

    @Test
    public void testGetStats() {
        assertEquals(basketball.getStats(), "45 mins at average pace");
    }

    @Test
    public void testGetDuration() {
        assertEquals(basketball.getDuration(), 45);
    }

    @Test
    public void testSetDuration() {
        basketball.setDuration(55);
        assertEquals(basketball.getDuration(), 55);
    }

}