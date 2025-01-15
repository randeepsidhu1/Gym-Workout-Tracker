import model.BestExerciseLog;
import model.Exercise;
import model.Goal;
import model.GoalsList;
import model.Workout;
import persistence.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Code/format used from JsonSerializationDemo
class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readCurrentWorkout();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderCurrentWorkout() {
        JsonReader reader = new JsonReader("./data/testCurrentWorkout.json");
        try {
            Workout workout = reader.readCurrentWorkout();
            assertEquals(LocalDateTime.parse("2024-07-24T14:00:00"), workout.getDateTimeCreated());
            List<Exercise> exercises = workout.getExercises();
            assertEquals(2, exercises.size());
            checkExercise("Push-up", 3, 12, 0, exercises.get(0));
            checkExercise("Squat", 3, 15, 50, exercises.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderBestExerciseLog() {
        JsonReader reader = new JsonReader("./data/testBestExerciseLog.json");
        try {
            BestExerciseLog bestExerciseLog = reader.readBestExerciseLog();
            List<Exercise> exercises = bestExerciseLog.getAllExercises();
            assertEquals(2, exercises.size());
            checkExercise("Bench Press", 4, 10, 100, exercises.get(0));
            checkExercise("Deadlift", 4, 8, 150, exercises.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGoalsList() {
        JsonReader reader = new JsonReader("./data/testGoalsList.json");
        try {
            GoalsList goalsList = reader.readGoalsList();
            List<Goal> goals = goalsList.getGoalsList();
            assertEquals(2, goals.size());
            checkGoal("Bench Press", 120, goals.get(0));
            checkGoal("Deadlift", 160, goals.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void checkExercise(String name, int sets, int reps, int weight, Exercise exercise) {
        assertEquals(name, exercise.getName());
        assertEquals(sets, exercise.getSets());
        assertEquals(reps, exercise.getReps());
        assertEquals(weight, exercise.getWeight());
    }

    private void checkGoal(String exerciseName, int targetWeight, Goal goal) {
        assertEquals(exerciseName, goal.getExerciseName());
        assertEquals(targetWeight, goal.getTargetWeight());
    }
}
