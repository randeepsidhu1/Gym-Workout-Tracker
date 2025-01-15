package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a list which keeps track of personal bests
public class BestExerciseLog implements Writable {

    private List<Exercise> exercisesRecords;

    // EFFECTS: Constructor creates an empty list of exercises
    public BestExerciseLog() {
        this.exercisesRecords = new ArrayList<>();
    }

    // EFFECTS: Returns an exercise from the records if found,
    // otherwise returnsnull.
    private Exercise existingExercise(String name) {
        for (Exercise exercise : exercisesRecords) {
            if (exercise.getName().equals(name)) {
                return exercise;

            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Adds the exercise to records if it's new and deletes old one
    // or updates it if its weight is higher than the existing one.
    public void addPrExercise(Exercise exercise) {
        Exercise existingExercise = existingExercise(exercise.getName());

        if (existingExercise != null) {
            if (exercise.getWeight() > existingExercise.getWeight()) {
                exercisesRecords.add(exercise);
                exercisesRecords.remove(existingExercise);
                EventLog.getInstance().logEvent(new Event(
                        "Updated exercise: " + exercise.getName() + " with new PR weight " + exercise.getWeight()));
            }
        } else {
            exercisesRecords.add(exercise);
            EventLog.getInstance().logEvent(
                    new Event("Added new PR exercise: " + exercise.getName() + " with weight " + exercise.getWeight()));
        }
    }

    // EFFECTS: Returns a list containing all exercises recorded in the log.
    public List<Exercise> getAllExercises() {
        return exercisesRecords;
    }

    // EFFECTS: Returns the highest weight recorded for the exercise with the
    // specified name, or null if not found.
    public Integer getBestWeight(String name) {
        for (Exercise exercise : exercisesRecords) {
            if (exercise.getName().equals(name)) {
                return exercise.getWeight();
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("bestExerciseLog", exercisesToJson());
        return json;
    }

    // EFFECTS: returns exercises in the log as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise e : exercisesRecords) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

}
