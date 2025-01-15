package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a Workout filled with exercises
public class Workout implements Writable {

    private List<Exercise> exercises;
    private LocalDateTime dateTimeCreated;

    // EFFECTS: Creates a new workout with the current date and time
    public Workout() {
        this.exercises = new ArrayList<>();
        this.dateTimeCreated = LocalDateTime.now();
    }

    // MODIFIES: this
    // EFFECTS: Adds an exercise to the workout
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        EventLog.getInstance().logEvent(new Event("Added exercise to workout: " + exercise.getName()));
    }

    // REQUIRES: exercise exists in the workout
    // MODIFIES: this
    // EFFECTS: Removes an exercise from the workout
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        EventLog.getInstance().logEvent(new Event("Removed exercise from workout: " + exercise.getName()));
    }

    // EFFECTS: Returns a list of all exercises in the workout
    public List<Exercise> getExercises() {
        return new ArrayList<>(exercises);
    }

    // EFFECTS: Returns the date and time the workout was created
    public LocalDateTime getDateTimeCreated() {
        return this.dateTimeCreated;
    }

    // MODIFIES: this
    // EFFECTS: Sets the date and time the workout was created
    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dateTimeCreated", dateTimeCreated.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns exercises in the workout as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}
