package persistence;

import model.CardioExercise;
import model.Exercise;
import model.UserWorkouts;
import model.WorkoutRoutine;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {
    protected void checkWorkoutRoutine(String date, String completed, WorkoutRoutine workoutRoutine) {
        assertEquals(LocalDate.parse(date), workoutRoutine.getDate());
        assertEquals(Boolean.valueOf(completed), workoutRoutine.isCompleted());
    }

    protected void checkExercise(String name, Exercise exercise) {
        assertEquals(name, exercise.getName());
    }
}