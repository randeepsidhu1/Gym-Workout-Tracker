package persistence;

import model.BestExerciseLog;
import model.EventLog;
import model.GoalsList;
import model.Workout;
import org.json.JSONObject;
import model.Event;

import java.io.*;

// Code/format used from JsonSerializationDemo
// Represents a writer that writes JSON representation of workout, bestexerciselog 
// and goalslist to file
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of BestExerciseLog to file
    public void writeExerciseLog(BestExerciseLog bestExerciseLog) {
        JSONObject json = bestExerciseLog.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved BestExerciseLog to " + destination));

    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of GoalsList to file
    public void writeGoallist(GoalsList goalsList) {
        JSONObject json = goalsList.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved GoalsList to " + destination));

    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Workout to file
    public void writeWorkout(Workout workout) {
        JSONObject json = workout.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved Workout to " + destination));

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
