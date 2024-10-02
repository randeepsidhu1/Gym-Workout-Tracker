package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

// Represents a WorkoutRoutine with a list of exercises and a date
public class WorkoutRoutine {

    private LocalDate date;
    private ArrayList<Exercise> exercises;
    private boolean completed;

    // MODIFIES: this
    // EFFECTS: Creates a Workout Routine which isn't complete with a given date and an empty list of exercises
    public WorkoutRoutine(LocalDate date) {
        this.date = date;
        this.exercises = new ArrayList<>();
        this.completed = false;
    }

    // EFFECTS: Returns the sum of calories burned of all exercises in this workout routine
    public int totalCalsBurned() {
        int count = 0;
        for (Exercise exercise : exercises) {
            count += exercise.getCalsBurned();
        }
        return count;
    }

    // EFFECTS: Returns the sum of distance travelled of all exercises in this workout routine
    public double totalDistanceTravelled() {
        double count = 0;
        for (Exercise exercise : exercises) {
            count += exercise.getDistanceTravelled();
        }
        return count;
    }

    // EFFECTS: Returns JSON representation of this workout routine
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date.toString());
        json.put("completed", String.valueOf(completed));
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: Returns JSON representation of exercises
    private JSONArray exercisesToJson() {
        JSONArray json = new JSONArray();
        for (Exercise exercise : exercises) {
            json.put(exercise.toJson());
        }
        return json;
    }

    // EFFECTS: Adds Exercise to workout routine
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        EventLog.getInstance().logEvent(new Event("Exercise `" + exercise.getName() + "` added to Workout on "
                + date.toString()));
    }

    // EFFECTS: Removes exercise from workout routine
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        EventLog.getInstance().logEvent(new Event("Exercise `" + exercise.getName()
                + "` removed from Workout on " + date.toString()));
    }

    // EFFECTS: Removes exercise from workout routine given index
    public void removeExercise(Integer i) {
        Exercise tempExercise = exercises.get(i);
        exercises.remove(tempExercise);
        EventLog.getInstance().logEvent(new Event("Exercise `" + tempExercise.getName()
                + "` removed from Workout on " + date.toString()));
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Exercise> getExercises() {
        return this.exercises;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
