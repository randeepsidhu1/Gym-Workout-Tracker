package model;

import org.json.JSONObject;

import java.time.LocalDate;

// Represents a Body weight Exercise with its name and stats
public class BodyWeightExercise extends Exercise {

    private static final double CALSPERREP_PUSHUPS = 0.32;
    private static final int CALSPERREP_PULLUPS = 1;
    private static final double CALSPERREP_CRUNCHES = 0.7;
    private static final int CALSPERREP_JUMPINGJACKS = 1;
    private static final double CALSPERREP_SITUPS = 0.5;
    private static final double SPORTS_DIST = 0;

    private int sets;
    private int reps;

    // REQUIRES: name is one of pushups, pullups, crunches, jumpingjacks, situps
    // EFFECTS: Creates a new body weight exercise with a name, sets, and reps
    public BodyWeightExercise(String name, int sets, int reps) {
        super(name, SPORTS_DIST);
        this.sets = sets;
        this.reps = reps;
        switch (name) {
            case "pushups": calsBurned = (int) (sets * reps * CALSPERREP_PUSHUPS);
                break;
            case "pullups": calsBurned = sets * reps * CALSPERREP_PULLUPS;
                break;
            case "crunches": calsBurned = (int) (sets * reps * CALSPERREP_CRUNCHES);
                break;
            case "jumpingjacks": calsBurned = sets * reps * CALSPERREP_JUMPINGJACKS;
                break;
            default: calsBurned = (int) (sets * reps * CALSPERREP_SITUPS);
                break;
        }
    }

    // EFFECTS: Returns how many sets and reps are in this exercise in string format
    @Override
    public String getStats() {
        return String.valueOf(sets) + "x" + String.valueOf(reps) + " reps";
    }

    // EFFECTS: Returns JSON representation of this exercise
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", "bodyWeight");
        json.put("name", name);
        json.put("sets", sets);
        json.put("reps", reps);
        return json;
    }

    public int getSets() {
        return this.sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return this.reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
