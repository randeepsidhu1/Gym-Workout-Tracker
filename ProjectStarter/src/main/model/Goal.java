package model;

import org.json.JSONObject;

import persistence.Writable;

// A class representing a Goal with its corresponding exercise name and weight
public class Goal implements Writable {

    private String exerciseName;
    private Integer targetWeight;

    // EFFECTS: Creates a new exercise goal with a description and target value
    public Goal(String name, Integer targetWeight) {
        this.exerciseName = name;
        this.targetWeight = targetWeight;
    }

    // EFFECTS: Returns the description of this exercise goal
    public String getExerciseName() {
        return exerciseName;
    }

    // EFFECTS: Returns the target value for this exercise goal
    public Integer getTargetWeight() {
        return targetWeight;
    }

    // MODIFIES: this
    // EFFECTS: Sets the description of this exercise goal
    public void setExerciseName(String name) {
        this.exerciseName = name;
    }

    // MODIFIES: this
    // EFFECTS: Sets the target value for this exercise goal
    public void setTargetWeight(Integer targetWeight) {
        this.targetWeight = targetWeight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("exerciseName", exerciseName);
        json.put("targetWeight", targetWeight);
        return json;
    }

}
