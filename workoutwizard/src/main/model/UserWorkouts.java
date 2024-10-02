package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

// Represents a list of all of a users workout routines
public class UserWorkouts {

    private ArrayList<WorkoutRoutine> workouts;

    // EFFECTS: Creates a new list of workouts where WorkoutRoutine's can be added
    public UserWorkouts() {
        this.workouts = new ArrayList<>();
    }

    // EFFECTS: Returns total calories burned in workouts completed set for current day
    public int getCalsBurnedToday() {
        int count = 0;
        LocalDate ld = LocalDate.now();
        for (WorkoutRoutine workout : workouts) {
            if (workout.isCompleted() && workout.getDate().isEqual(ld)) {
                count += workout.totalCalsBurned();
            }
        }
        return count;
    }

    // EFFECTS: Returns total calories burned in workouts completed set for a date in last 7 days
    public int getCalsBurnedThisWeek() {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (WorkoutRoutine workout : workouts) {
            LocalDate wkDate = workout.getDate();
            if (workout.isCompleted() && !wkDate.isBefore(today.minusDays(6)) && !wkDate.isAfter(today)) {
                count += workout.totalCalsBurned();
            }
        }
        return count;
    }

    // EFFECTS: Returns total distance travelled in workouts completed set for current day
    public double getDistanceTravelledToday() {
        double count = 0;
        LocalDate ld = LocalDate.now();
        for (WorkoutRoutine workout : workouts) {
            if (workout.isCompleted() && workout.getDate().isEqual(ld)) {
                count += workout.totalDistanceTravelled();
            }
        }
        return count;
    }

    // EFFECTS: Returns total distance travelled in workouts completed set for a date in last 7 days
    public double getDistanceTravelledThisWeek() {
        double count = 0;
        LocalDate ld = LocalDate.now();
        for (WorkoutRoutine workout : workouts) {
            LocalDate wkDate = workout.getDate();
            if (workout.isCompleted() && wkDate.isAfter(ld.minusDays(8)) && wkDate.isBefore(ld.plusDays(1))) {
                count += workout.totalDistanceTravelled();
            }
        }
        return count;
    }

    // MODIFIES: this
    // EFFECTS: Adds a workout routine to list of users workout routines
    public void addWorkoutRoutine(WorkoutRoutine workoutRoutine) {
        workouts.add(workoutRoutine);
        EventLog.getInstance().logEvent(new Event("New Workout created on " + workoutRoutine.getDate().toString()));
    }

    // MODIFIES: this
    // EFFECTS: Removes a workout routine from list of users workout routines
    public void removeWorkoutRoutine(WorkoutRoutine workoutRoutine) {
        workouts.remove(workoutRoutine);
        EventLog.getInstance().logEvent(new Event("Workout on " + workoutRoutine.getDate().toString() + "removed"));
    }

    // EFFECTS: Returns JSON representation of User's Workout
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (WorkoutRoutine workout : workouts) {
            jsonArray.put(workout.toJson());
        }
        return jsonArray;
    }

    public ArrayList<WorkoutRoutine> getWorkouts() {
        return this.workouts;
    }
}
