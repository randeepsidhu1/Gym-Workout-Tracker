package model;

import org.json.JSONObject;

// Represents an abstract exercise with a name and stats
public abstract class Exercise {

    protected String name;
    protected Integer calsBurned;
    protected Double distanceTravelled;

    // MODIFIES: this
    // EFFECTS: Creates a new exercise with a name and distanceTravelled
    public Exercise(String name, Double distanceTravelled) {
        this.name = name;
        this.distanceTravelled = distanceTravelled;
    }

    public abstract String getStats();

    public String getName() {
        return this.name;
    }

    public Integer getCalsBurned() {
        return this.calsBurned;
    }

    public Double getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public abstract JSONObject toJson();
}
