package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a list of Goals
public class GoalsList implements Writable {

    private List<Goal> goalsList;

    // EFFECTS: Creates a empty list of Goals
    public GoalsList() {
        this.goalsList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds a goal to the goals list
    public void addGoalToList(Goal goal) {
        goalsList.add(goal);
        EventLog.getInstance().logEvent(
                new Event("Added goal: " + goal.getExerciseName() + " with target weight " + goal.getTargetWeight()));
    }

    // EFFECTS: Returns the goals list
    public List<Goal> getGoalsList() {
        return goalsList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("goalsList", goalsToJson());
        return json;
    }

    // EFFECTS: returns goals in the list as a JSON array
    private JSONArray goalsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Goal g : goalsList) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }
}
