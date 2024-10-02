package persistence;

import model.Event;
import model.EventLog;
import model.UserWorkouts;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

// Represents a writer that writes to JSON a representation of the user's workouts
public class JsonWriter {
    private static final Integer INDENT_FACTOR = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs JsonWriter with given destination path for file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens a new writer. Throws FileNotFoundException if destination file cannot
    //          be opened
    public void openFile() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of user's workouts to JSON file
    public void write(UserWorkouts workouts) {
        JSONArray json = workouts.toJson();
        saveToFile(json.toString(INDENT_FACTOR));
        EventLog.getInstance().logEvent(new Event("Saved workouts to file"));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes given string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
