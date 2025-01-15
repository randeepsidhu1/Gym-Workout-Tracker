import persistence.JsonWriter;
import persistence.JsonReader;
import model.BestExerciseLog;
import model.Exercise;
import model.Goal;
import model.GoalsList;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// Code/format used from JsonSerializationDemo
class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterCurrentWorkout() {
        try {
            Workout workout = new Workout();
            workout.setDateTimeCreated(LocalDateTime.parse("2024-07-24T14:00:00"));
            workout.addExercise(new Exercise("Push-up", 3, 12, 0));
            workout.addExercise(new Exercise("Squat", 3, 15, 50));

            JsonWriter writer = new JsonWriter("./data/testWriterCurrentWorkout.json");
            writer.open();
            writer.writeWorkout(workout);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCurrentWorkout.json");
            workout = reader.readCurrentWorkout();
            assertEquals(LocalDateTime.parse("2024-07-24T14:00:00"), workout.getDateTimeCreated());
            List<Exercise> exercises = workout.getExercises();
            assertEquals(2, exercises.size());
            checkExercise("Push-up", 3, 12, 0, exercises.get(0));
            checkExercise("Squat", 3, 15, 50, exercises.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterBestExerciseLog() {
        try {
            BestExerciseLog bestExerciseLog = new BestExerciseLog();
            bestExerciseLog.addPrExercise(new Exercise("Bench Press", 4, 10, 100));
            bestExerciseLog.addPrExercise(new Exercise("Deadlift", 4, 8, 150));

            JsonWriter writer = new JsonWriter("./data/testWriterBestExerciseLog.json");
            writer.open();
            writer.writeExerciseLog(bestExerciseLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBestExerciseLog.json");
            bestExerciseLog = reader.readBestExerciseLog();
            List<Exercise> exercises = bestExerciseLog.getAllExercises();
            assertEquals(2, exercises.size());
            checkExercise("Bench Press", 4, 10, 100, exercises.get(0));
            checkExercise("Deadlift", 4, 8, 150, exercises.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGoalsList() {
        try {
            GoalsList goalsList = new GoalsList();
            goalsList.addGoalToList(new Goal("Bench Press", 120));
            goalsList.addGoalToList(new Goal("Deadlift", 160));

            JsonWriter writer = new JsonWriter("./data/testWriterGoalsList.json");
            writer.open();
            writer.writeGoallist(goalsList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGoalsList.json");
            goalsList = reader.readGoalsList();
            List<Goal> goals = goalsList.getGoalsList();
            assertEquals(2, goals.size());
            checkGoal("Bench Press", 120, goals.get(0));
            checkGoal("Deadlift", 160, goals.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
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