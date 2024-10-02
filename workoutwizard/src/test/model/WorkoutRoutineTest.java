package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutRoutineTest {

    private Exercise running;
    private Exercise pushUps;
    private Exercise basketball;
    private WorkoutRoutine myWorkout;
    private LocalDate today;

    @BeforeEach
    public void setUp() {
        today = LocalDate.now();
        running = new CardioExercise("running", 30, 4.0);
        pushUps = new BodyWeightExercise("pushups", 3, 12);
        basketball = new SportExercise("basketball", 60, "average");
        myWorkout = new WorkoutRoutine(today);
    }

    @Test
    public void testTotalCalsBurned() {
        assertEquals(myWorkout.totalCalsBurned(), 0);
        myWorkout.addExercise(running);
        myWorkout.addExercise(pushUps);
        myWorkout.addExercise(basketball);
        assertEquals(myWorkout.totalCalsBurned(), 360 + 11 + 840);
    }

    @Test
    public void testTotalDistanceTravelled() {
        assertEquals(myWorkout.totalDistanceTravelled(), 0);
        myWorkout.addExercise(running);
        myWorkout.addExercise(pushUps);
        assertEquals(myWorkout.totalDistanceTravelled(), 4.0);
    }

    @Test
    public void testGetDate() {
        assertEquals(myWorkout.getDate(), today);
    }

    @Test
    public void testSetDate() {
        myWorkout.setDate(myWorkout.getDate().plusDays(2));
        assertEquals(myWorkout.getDate(), today.plusDays(2));
    }

    @Test
    public void testGetAndAddAndRemoveExercises() {
        assertTrue(myWorkout.getExercises().isEmpty());
        myWorkout.addExercise(running);
        myWorkout.addExercise(pushUps);
        myWorkout.addExercise(basketball);
        assertEquals(myWorkout.getExercises().get(0), running);
        assertEquals(myWorkout.getExercises().get(1), pushUps);
        myWorkout.removeExercise(1);
        assertEquals(myWorkout.getExercises().size(), 2);
    }

    @Test
    public void testIsCompleted() {
        assertFalse(myWorkout.isCompleted());
    }

    @Test
    public void testSetCompleted() {
        myWorkout.setCompleted(true);
        assertTrue(myWorkout.isCompleted());
    }
}