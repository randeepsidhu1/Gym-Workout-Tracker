package model;

import org.json.JSONObject;

import java.time.LocalDate;

// Represents a Weight Training Exercise with its name, date, and stats
public class WeightTrainingExercise extends Exercise {

    private static final int CALSPERREP_SQUAT = 2;
    private static final int CALSPERREP_LEGPRESS = 1;
    private static final int CALSPERREP_LUNGE = 2;
    private static final int CALSPERREP_DEADLIFT = 1;
    private static final int CALSPERREP_BENCH = 1;
    private static final int CALSPERREP_SHOULDERPRESS = 2;
    private static final int CALSPERREP_DUMBBELLS = 1;
    private static final double SPORTS_DIST = 0;

    private int sets;
    private int reps;

    // REQUIRES: name is one of squat, legpress, lunge, deadlift, bench, shoulderpress, dumbbells
    // EFFECTS: Creates a new weightraining exercise with a name, sets, and reps
    public WeightTrainingExercise(String name, int sets, int reps) {
        super(name, SPORTS_DIST);
        this.sets = sets;
        this.reps = reps;
        switch (name) {
            case "squat": calsBurned = sets * reps * CALSPERREP_SQUAT;
                break;
            case "legpress": calsBurned = sets * reps * CALSPERREP_LEGPRESS;
                break;
            case "lunge": calsBurned = sets * reps * CALSPERREP_LUNGE;
                break;
            case "deadlift": calsBurned = sets * reps * CALSPERREP_DEADLIFT;
                break;
            case "bench": calsBurned = sets * reps * CALSPERREP_BENCH;
                break;
            case "shoulderpress": calsBurned = sets * reps * CALSPERREP_SHOULDERPRESS;
                break;
            default: calsBurned = sets * reps * CALSPERREP_DUMBBELLS;
                break;
        }
    }

    // EFFECTS: Returns JSON representation of this exercise
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", "weightTraining");
        json.put("name", name);
        json.put("sets", sets);
        json.put("reps", reps);
        return json;
    }

    // EFFECTS: Returns how many sets and reps are in this exercise in string format
    @Override
    public String getStats() {
        return String.valueOf(sets) + "x" + String.valueOf(reps) + " reps";
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
