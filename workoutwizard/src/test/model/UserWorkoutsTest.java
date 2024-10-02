package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserWorkoutsTest {

    private Exercise running;
    private Exercise pushUps;
    private Exercise basketball;
    private WorkoutRoutine myWorkout1;
    private Exercise walking;
    private Exercise bench;
    private Exercise soccer;
    private WorkoutRoutine myWorkout2;
    public Exercise biking;
    public WorkoutRoutine myWorkout3;
    private LocalDate today;

    private UserWorkouts userWorkouts;

    @BeforeEach
    public void setUp() {
        today = LocalDate.now();
        running = new CardioExercise("running", 30, 4.0);
        pushUps = new BodyWeightExercise("pushups", 3, 12);
        basketball = new SportExercise("basketball", 60, "average");
        myWorkout1 = new WorkoutRoutine(today);
        myWorkout1.addExercise(running);
        myWorkout1.addExercise(pushUps);
        myWorkout1.addExercise(basketball);
        walking = new CardioExercise("walking", 60, 3.0);
        bench = new WeightTrainingExercise("bench", 2, 10);
        soccer = new SportExercise("soccer", 90, "intense");
        myWorkout2 = new WorkoutRoutine(today.minusDays(6));
        myWorkout2.addExercise(walking);
        myWorkout2.addExercise(bench);
        myWorkout2.addExercise(soccer);
        biking = new CardioExercise("biking", 45, 12.0);
        myWorkout3 = new WorkoutRoutine(today);
        myWorkout3.addExercise(biking);
        userWorkouts = new UserWorkouts();
    }

    @Test
    public void testAddAndGetWorkoutRoutine() {
        assertTrue(userWorkouts.getWorkouts().isEmpty());
        userWorkouts.addWorkoutRoutine(myWorkout1);
        userWorkouts.addWorkoutRoutine(myWorkout2);
        userWorkouts.addWorkoutRoutine(myWorkout3);
        assertEquals(userWorkouts.getWorkouts().size(), 3);
    }

    @Test
    public void testGetCalsBurnedToday() {
        userWorkouts.addWorkoutRoutine(myWorkout2);
        assertEquals(userWorkouts.getCalsBurnedToday(), 0);
        myWorkout2.setCompleted(true);
        assertEquals(userWorkouts.getCalsBurnedToday(), 0);
        userWorkouts.addWorkoutRoutine(myWorkout1);
        userWorkouts.addWorkoutRoutine(myWorkout3);
        myWorkout1.setCompleted(true);
        myWorkout3.setCompleted(true);
        assertEquals(userWorkouts.getCalsBurnedToday(), 1211 + 270);
    }

    @Test
    public void testGetCalsBurnedThisWeek() {
        userWorkouts.addWorkoutRoutine(myWorkout2);
        assertEquals(userWorkouts.getCalsBurnedThisWeek(), 0);
        myWorkout2.setCompleted(true);
        assertEquals(userWorkouts.getCalsBurnedThisWeek(), 2060);
        myWorkout3.setDate(today.minusDays(8));
        userWorkouts.addWorkoutRoutine(myWorkout3);
        myWorkout1.setDate(today.plusDays(1));
        userWorkouts.addWorkoutRoutine(myWorkout1);
        myWorkout3.setCompleted(true);
        myWorkout1.setCompleted(true);
        assertEquals(userWorkouts.getCalsBurnedThisWeek(), 2060);
    }

    @Test
    public void testGetDistanceTravelledToday() {
        userWorkouts.addWorkoutRoutine(myWorkout2);
        assertEquals(userWorkouts.getDistanceTravelledToday(), 0.0);
        myWorkout2.setCompleted(true);
        assertEquals(userWorkouts.getDistanceTravelledToday(), 0.0);
        userWorkouts.addWorkoutRoutine(myWorkout1);
        userWorkouts.addWorkoutRoutine(myWorkout3);
        myWorkout1.setCompleted(true);
        myWorkout3.setCompleted(true);
        assertEquals(userWorkouts.getDistanceTravelledToday(), 16.0);
    }

    @Test
    public void testGetDistanceTravelledThisWeek() {
        userWorkouts.addWorkoutRoutine(myWorkout2);
        assertEquals(userWorkouts.getDistanceTravelledThisWeek(), 0);
        myWorkout2.setCompleted(true);
        assertEquals(userWorkouts.getDistanceTravelledThisWeek(), 3.0);
        myWorkout3.setDate(today.minusDays(8));
        userWorkouts.addWorkoutRoutine(myWorkout3);
        myWorkout1.setDate(today.plusDays(1));
        userWorkouts.addWorkoutRoutine(myWorkout1);
        myWorkout3.setCompleted(true);
        myWorkout1.setCompleted(true);
        assertEquals(userWorkouts.getDistanceTravelledThisWeek(), 3.0);
    }
}