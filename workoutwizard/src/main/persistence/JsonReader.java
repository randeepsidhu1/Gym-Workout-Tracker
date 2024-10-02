package persistence;

import com.sun.jdi.request.ExceptionRequest;
import model.*;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

// Represents a json reader that reads user workouts from JSON data
public class JsonReader {
    private String source;

    // EFFECTS: Constructs a JsonReader to read from a source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads user's workouts from file and returns it
    public UserWorkouts read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        EventLog.getInstance().logEvent(new Event("Saved workouts read from file"));
        return parseUserWorkouts(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s));
        }
        return stringBuilder.toString();
    }

    // EFFECTS: Parses UserWorkouts from given Json Array and returns it
    private UserWorkouts parseUserWorkouts(JSONArray jsonArray) {
        UserWorkouts userWorkouts = new UserWorkouts();
        addWorkoutRoutines(userWorkouts, jsonArray);
        return userWorkouts;
    }

    // MODIFIES: userWorkouts
    // EFFECTS: parses WorkoutRoutines from JSON Array and adds it to userWorkouts
    private void addWorkoutRoutines(UserWorkouts userWorkouts, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject workoutRoutineObject = (JSONObject) json;
            addWorkoutRoutine(userWorkouts, workoutRoutineObject);
        }
    }

    // MODIFIES: userWorkouts
    // EFFECTS: parses Workout Routine from JSON Object and adds it to workout routine and adds to UserWorkouts
    private void addWorkoutRoutine(UserWorkouts userWorkouts, JSONObject workoutRoutineObject) {
        LocalDate date = LocalDate.parse(workoutRoutineObject.getString("date"));
        WorkoutRoutine workoutRoutine = new WorkoutRoutine(date);
        workoutRoutine.setCompleted(Boolean.parseBoolean(workoutRoutineObject.getString("completed")));
        addExercises(workoutRoutine, workoutRoutineObject.getJSONArray("exercises"));
        userWorkouts.addWorkoutRoutine(workoutRoutine);
    }

    // EFFECTS: Adds exercises to workout routine
    private void addExercises(WorkoutRoutine workoutRoutine, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject) json;
            addExercise(workoutRoutine, jsonObject);
        }
    }

    // EFFECTS: Adds specified exercise to workout routine
    private void addExercise(WorkoutRoutine workoutRoutine, JSONObject jsonObject) {
        switch (jsonObject.getString("type")) {
            case "cardio":
                addCardioExercise(workoutRoutine, jsonObject);
                break;
            case "weightTraining":
                addWeightExercise(workoutRoutine, jsonObject);
                break;
            case "bodyWeight":
                addBodyExercise(workoutRoutine, jsonObject);
                break;
            default:
                addSportExercise(workoutRoutine, jsonObject);
                break;
        }
    }

    // MODIFIES: workoutRoutine
    // EFFECTS: Parses given json and adds exercise to workout routine
    private void addCardioExercise(WorkoutRoutine workoutRoutine, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int duration = jsonObject.getInt("duration");
        double distance = jsonObject.getDouble("distance");
        workoutRoutine.addExercise(new CardioExercise(name, duration, distance));
    }

    // MODIFIES: workoutRoutine
    // EFFECTS: Parses given json and adds exercise to workout routine
    private void addWeightExercise(WorkoutRoutine workoutRoutine, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        workoutRoutine.addExercise(new WeightTrainingExercise(name, sets, reps));
    }

    // MODIFIES: workoutRoutine
    // EFFECTS: Parses given json and adds exercise to workout routine
    private void addBodyExercise(WorkoutRoutine workoutRoutine, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        workoutRoutine.addExercise(new BodyWeightExercise(name, sets, reps));
    }

    // MODIFIES: workoutRoutine
    // EFFECTS: Parses given json and adds exercise to workout routine
    private void addSportExercise(WorkoutRoutine workoutRoutine, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int duration = jsonObject.getInt("duration");
        String intensity = jsonObject.getString("intensity");
        workoutRoutine.addExercise(new SportExercise(name, duration, intensity));
    }
}
