package model;

import org.json.JSONObject;

import java.time.LocalDate;

// Represents a Cardio Exercise with its name, and stats
public class CardioExercise extends Exercise {

    private static final int CALSPERMIN_RUNNING = 12;
    private static final int CALSPERMIN_BIKING = 6;
    private static final int CALSPERMIN_WALKING = 4;
    private static final int CALSPERMIN_SWIMMING = 10;

    private Integer duration;
    private Double distance;

    // REQUIRES: name one of running, biking, walking, swimming
    // EFFECTS: Creates a new Cardio Exercise with a name, duration in minutes, distance in kilometers
    public CardioExercise(String name, Integer duration, Double distance) {
        super(name, distance);
        this.duration = duration;
        this.distance = distance;
        switch (name) {
            case "running":
                calsBurned = CALSPERMIN_RUNNING * duration;
                break;
            case "biking":
                calsBurned = CALSPERMIN_BIKING * duration;
                break;
            case "walking":
                calsBurned = CALSPERMIN_WALKING * duration;
                break;
            default:
                calsBurned = CALSPERMIN_SWIMMING * duration;
                break;
        }
    }

    // EFFECTS: Returns JSON representation of this exercise
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", "cardio");
        json.put("name", name);
        json.put("duration", duration);
        json.put("distance", distance);
        return json;
    }

    // EFFECTS: Returns the length of the exercise in string format
    @Override
    public String getStats() {
        return duration.toString() + " mins and " + distance.toString() + "KM";
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
