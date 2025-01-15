package model;

import org.json.JSONObject;

import persistence.Writable;

//Represents a Exercise in a workout with a given name, 
// the number of sets and reps to do
public class Exercise implements Writable {

    private String name;
    private Integer sets;
    private Integer reps;
    private Integer weight;

    // EFFECTS: Creates a new exercise with a name and distanceTravelled
    public Exercise(String name, Integer sets, Integer reps, Integer weight) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // EFFECTS: Returns the name for this exercise
    public String getName() {
        return this.name;
    }

    // EFFECTS: Returns the number of sets for this exercise
    public Integer getSets() {
        return this.sets;
    }

    // EFFECTS: Returns the number of reps for this exercise
    public Integer getReps() {
        return this.reps;
    }

    // MODIFIES: this
    // EFFECTS: Sets the number of reps for this exercise
    public Integer getWeight() {
        return this.weight;
    }

    // MODIFIES: this
    // EFFECTS: Sets the number of sets for this exercise
    public void setSets(Integer sets) {
        this.sets = sets;
    }

    // MODIFIES: this
    // EFFECTS: Sets the number of reps for this exercise
    public void setReps(Integer reps) {
        this.reps = reps;
    }

    // MODIFIES: this
    // EFFECTS: Sets the number of reps for this exercise
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sets", sets);
        json.put("reps", reps);
        json.put("weight", weight);
        return json;
    }
}
