package ui;

import java.io.FileNotFoundException;

public class Main {
    // EFFECTS: Runs the application
    public static void main(String[] args) {
        try {
            new WorkoutTracker(); // Initializes the application
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}