package model;

import org.json.JSONObject;

import java.time.LocalDate;

// Represents a Sports Exercise with its name and stats
public class SportExercise extends Exercise {
    private static final int CALSPERMIN_BASKETBALL = 14;
    private static final int CALSPERMIN_SOCCER = 10;
    private static final int CALSPERMIN_HOCKEY = 15;
    private static final int CALSPERMIN_BASEBALL = 4;
    private static final double SPORTS_DIST = 0;

    private Integer duration;
    private String intensity;

    // REQUIRES: name one of basketball, soccer, hockey, baseball.
    //           intensity one of light, average, intense
    // EFFECTS: Creates a new Sports Exercise with a name, duration in minutes, intensity
    public SportExercise(String name, Integer duration, String intensity) {
        super(name, SPORTS_DIST);
        this.duration = duration;
        this.intensity = intensity;
        switch (name) {
            case "basketball": calsBurned = CALSPERMIN_BASKETBALL * duration;
                break;
            case "soccer": calsBurned = CALSPERMIN_SOCCER * duration;
                break;
            case "hockey": calsBurned = CALSPERMIN_HOCKEY * duration;
                break;
            default: calsBurned = CALSPERMIN_BASEBALL * duration;
                break;
        }
        switch (intensity) {
            case "light": calsBurned = calsBurned / 2;
                break;
            case "average":
                break;
            default: calsBurned = calsBurned * 2;
                break;
        }
    }

    // EFFECTS: Returns JSON representation of this exercise
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", "sport");
        json.put("name", name);
        json.put("duration", duration);
        json.put("intensity", intensity);
        return json;
    }

    // EFFECTS: returns the duration of the exercise in string format
    @Override
    public String getStats() {
        return String.valueOf(duration) + " mins at " + intensity + " pace";
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
