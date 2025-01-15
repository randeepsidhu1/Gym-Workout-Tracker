package persistence;

import model.BestExerciseLog;
import model.EventLog;
import model.Exercise;
import model.Goal;
import model.GoalsList;
import model.Workout;
import model.Event;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.time.LocalDateTime;
import org.json.*;

// Code/format used from JsonSerializationDemo
// Represents a reader that reads Workout, BestExerciseLog and GoalsList
// from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads current workout data from file and returns it
    // throws IOException if an error occurs reading data from file
    public Workout readCurrentWorkout() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded current workout from " + source));
        return parseWorkout(jsonObject);
    }

    // EFFECTS: reads BestExerciseLog data from file and returns it
    // throws IOException if an error occurs reading data from file
    public BestExerciseLog readBestExerciseLog() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded BestExerciseLog from " + source));
        return parseBestExerciseLog(jsonObject);
    }

    // EFFECTS: reads GoalsList data from file and returns it
    // throws IOException if an error occurs reading data from file
    public GoalsList readGoalsList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded GoalsList from " + source));

        return parseGoalsList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses a single workout from JSON object and returns a Workout
    // object
    private Workout parseWorkout(JSONObject jsonObject) {
        Workout workout = new Workout();
        workout.setDateTimeCreated(LocalDateTime.parse(jsonObject.getString("dateTimeCreated")));
        JSONArray exercisesArray = jsonObject.getJSONArray("exercises");
        for (Object json : exercisesArray) {
            JSONObject exerciseJson = (JSONObject) json;
            workout.addExercise(parseExercise(exerciseJson));
        }
        return workout;
    }

    // EFFECTS: parses an exercise from JSON object
    private Exercise parseExercise(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        int weight = jsonObject.getInt("weight");
        return new Exercise(name, sets, reps, weight);
    }

    // EFFECTS: parses the best exercise log from JSON object and returns a
    // BestExerciseLog object
    private BestExerciseLog parseBestExerciseLog(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("bestExerciseLog");
        BestExerciseLog bestExerciseLog = new BestExerciseLog();
        for (Object json : jsonArray) {
            JSONObject exerciseJson = (JSONObject) json;
            bestExerciseLog.addPrExercise(parseExercise(exerciseJson));
        }
        return bestExerciseLog;
    }

    // EFFECTS: parses the goals list from JSON object and returns a GoalsList
    // object
    private GoalsList parseGoalsList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("goalsList");
        GoalsList goalsList = new GoalsList();
        for (Object json : jsonArray) {
            JSONObject goalJson = (JSONObject) json;
            goalsList.addGoalToList(parseGoal(goalJson));
        }
        return goalsList;
    }

    // EFFECTS: parses a goal from JSON object
    private Goal parseGoal(JSONObject jsonObject) {
        String exerciseName = jsonObject.getString("exerciseName");
        int targetWeight = jsonObject.getInt("targetWeight");
        return new Goal(exerciseName, targetWeight);
    }
}