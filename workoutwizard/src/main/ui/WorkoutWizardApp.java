package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDate;

// UI for users to navigate the application
public class WorkoutWizardApp {
    private static final String JSON_DEST = "./data/userworkouts.json";
    private UserWorkouts userWorkouts;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Starts the Workout Wizard App
    public WorkoutWizardApp() {
        userWorkouts = new UserWorkouts();
        jsonWriter = new JsonWriter(JSON_DEST);
        jsonReader = new JsonReader(JSON_DEST);
        startApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes user inputs as commands
    public void startApp() {
        boolean appActive = true;
        String command;
        input = new Scanner(System.in);

        System.out.println("Welcome to WorkoutWizard!");
        System.out.println("Would you like to load saved workouts?");
        command = input.next().toLowerCase();
        if (command.equals("yes")) {
            loadSavedWorkouts();
        }
        while (appActive) {
            showCommands();
            command = input.next().toLowerCase();

            if (command.equals("/quit")) {
                appActive = false;
                promptSave();
            } else {
                doCommand(command);
            }
        }
        printLog(EventLog.getInstance());
        System.out.println("Keep working hard! Goodbye.");
    }

    // EFFECTS: Prints each event time and description
    private void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // EFFECTS: Loads saved workouts
    private void loadSavedWorkouts() {
        try {
            userWorkouts = jsonReader.read();
            System.out.println("Loaded Workouts from " + JSON_DEST);
        } catch (IOException e) {
            System.out.println("Unable to read file: " + JSON_DEST);
        }
    }

    // EFFECTS: Prompts user if they want to save their workouts
    private void promptSave() {
        System.out.println("Do you want to save your workouts?");
        String response;
        input = new Scanner(System.in);
        response = input.next();
        if (response.equalsIgnoreCase("yes")) {
            saveWorkouts();
        }
    }

    // EFFECTS: Saves user workouts to a file
    private void saveWorkouts() {
        try {
            jsonWriter.openFile();
            jsonWriter.write(userWorkouts);
            jsonWriter.close();
            System.out.println("Saved workouts to " + JSON_DEST);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_DEST);
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes user command
    public void doCommand(String command) {
        switch (command) {
            case "/makeworkout":
                createWorkout();
                break;
            case "/viewworkouts":
                viewWorkouts();
                break;
            case "/statstoday":
                System.out.println(userWorkouts.getCalsBurnedToday() + " calories burned today");
                System.out.println(userWorkouts.getDistanceTravelledToday() + "km in cardio exercises");
                break;
            default:
                System.out.println(userWorkouts.getCalsBurnedThisWeek() + " calories burned in the last 7 days");
                System.out.println(userWorkouts.getDistanceTravelledThisWeek() + "km in cardio in the last 7 days");
                break;
        }
    }

    // EFFECTS: Creates new workout routine and allows user to add workouts to it
    public void createWorkout() {
        String command;
        System.out.println("Provide a date for this workout in yyyy-mm-dd format");
        command = input.next();
        WorkoutRoutine newWorkout = new WorkoutRoutine(LocalDate.parse(command));
        displayAndAddExercises(newWorkout);
        userWorkouts.addWorkoutRoutine(newWorkout);
    }

    // EFFECTS: Prints a list of all users' workouts in chronological order, allows user to select each one
    public void viewWorkouts() {
        userWorkouts.getWorkouts().sort(Comparator.comparing(WorkoutRoutine::getDate).reversed());
        int count = 1;
        for (WorkoutRoutine workoutRoutine : userWorkouts.getWorkouts()) {
            System.out.print("Workout Routine #" + count + " on " + workoutRoutine.getDate());
            System.out.println(" | Is complete? " + workoutRoutine.isCompleted());
            count++;
        }
        System.out.println("Select a workout or type /back");
        String selection;
        selection = input.next();
        if (!selection.equals("/back")) {
            viewSelectedWorkout(userWorkouts.getWorkouts().get(Integer.parseInt(selection) - 1));
        }
    }

    // EFFECTS: Prints out workout and allows user to edit it
    public void viewSelectedWorkout(WorkoutRoutine workoutRoutine) {
        System.out.print("Workout set for " + workoutRoutine.getDate());
        System.out.println(": Completed = " + workoutRoutine.isCompleted());
        if (workoutRoutine.isCompleted()) {
            System.out.println(workoutRoutine.totalCalsBurned() + " Cals Burned");
            System.out.println(workoutRoutine.totalDistanceTravelled() + "km travelled in this workout");
        }
        int count = 1;
        for (Exercise exercise : workoutRoutine.getExercises()) {
            System.out.print("#" + count + " ");
            System.out.print(exercise.getName().substring(0, 1).toUpperCase() + exercise.getName().substring(1));
            System.out.println(": " + exercise.getStats());
            count++;
        }
        viewSelectedWorkoutCommands();
        String selection;
        selection = input.next().toLowerCase();
        doCommandOnSelectedWorkout(workoutRoutine, selection);
    }

    // MODIFIES: workoutRoutine
    // EFFECTS: Asks for and parses user's command on given workout
    private void doCommandOnSelectedWorkout(WorkoutRoutine workoutRoutine, String selection) {
        switch (selection) {
            case "/setcomplete":
                askForSetComplete(workoutRoutine);
                break;
            case "/setdate":
                System.out.print("Type date in yyyy-mm-dd: ");
                selection = input.next();
                workoutRoutine.setDate(LocalDate.parse(selection));
                break;
            case "/addexercise":
                displayAndAddExercises(workoutRoutine);
                break;
            case "/deleteExercise":
                System.out.print("Pick # to delete: ");
                int i = input.nextInt();
                workoutRoutine.removeExercise(i - 1);
                break;
            default:
                break;
        }
    }

    // MODIFIES: workoutRoutine
    // EFFECTS: Asks user if they want to set Workout Routine as complete
    private void askForSetComplete(WorkoutRoutine workoutRoutine) {
        String selection;
        System.out.print("Yes or no? ");
        selection = input.next().toLowerCase();
        if (selection.equals("yes")) {
            workoutRoutine.setCompleted(true);
        }
    }

    // EFFECTS: Prints out all the exercises that may be added and allows user to add it
    public void displayAndAddExercises(WorkoutRoutine newWorkout) {
        showAddCommands();
        boolean stillAdding = true;
        while (stillAdding) {
            String command = input.next().toLowerCase();
            switch (command) {
                case "/addcardioexercise":
                    addCardioExercise(newWorkout);
                    break;
                case "/addweightliftexercise":
                    addWeightExercise(newWorkout);
                    break;
                case "/addbodyweightexercise":
                    addBodyExercise(newWorkout);
                    break;
                case "/addsport":
                    addSportExercise(newWorkout);
                    break;
                default:
                    stillAdding = false;
                    break;
            }
            System.out.println("Add another exercise or type /menu");
        }
    }

    // MODIFIES: newWorkout
    // EFFECTS: Adds Sport Exercise to given workout
    private void addSportExercise(WorkoutRoutine newWorkout) {
        String name;
        int duration;
        double distance;
        int sets;
        int reps;
        System.out.println("Add any of the following exercises by typing any of the following");
        System.out.println("[Basketball, Soccer, Hockey, Baseball.]");
        name = input.next();
        System.out.print("Duration in minutes: ");
        duration = input.nextInt();
        System.out.print("Pace [Light, Average, Intense]: ");
        String intensity = input.next();
        newWorkout.addExercise(new SportExercise(name, duration, intensity));
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " Added!");
    }

    // MODIFIES: newWorkout
    // EFFECTS: Adds Body weight Exercise to given workout
    private void addBodyExercise(WorkoutRoutine newWorkout) {
        String name;
        int duration;
        double distance;
        int sets;
        int reps;
        System.out.println("Add any of the following exercises by typing any of the following");
        System.out.println("[Pushups, Pullups, Crunches, Jumpingjacks, Situps]");
        name = input.next();
        System.out.print("How many sets?: ");
        sets = input.nextInt();
        System.out.print("How many reps?: ");
        reps = input.nextInt();
        newWorkout.addExercise(new BodyWeightExercise(name, sets, reps));
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " Added!");
    }

    // MODIFIES: newWorkout
    // EFFECTS: Adds Weight Exercise to given workout
    private void addWeightExercise(WorkoutRoutine newWorkout) {
        String name;
        int duration;
        double distance;
        int sets;
        int reps;
        System.out.println("Add any of the following exercises by typing any of the following");
        System.out.println("[Squat, Legpress, Lunge, Deadlift, Bench, Shoulderpress, Dumbbells]");
        name = input.next();
        System.out.print("How many sets?: ");
        sets = input.nextInt();
        System.out.print("How many reps?: ");
        reps = input.nextInt();
        newWorkout.addExercise(new WeightTrainingExercise(name, sets, reps));
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " Added!");
    }

    // MODIFIES: newWorkout
    // EFFECTS: Adds cardio exercise to given workout
    private void addCardioExercise(WorkoutRoutine newWorkout) {
        String name;
        int duration;
        double distance;
        int sets;
        int reps;
        String intensity;
        System.out.println("Add any of the following exercises by typing any of the following");
        System.out.println("[Running, Biking, Walking, Swimming]");
        name = input.next();
        System.out.print("Duration in minutes: ");
        duration = input.nextInt();
        System.out.print("Distance in KM: ");
        distance = input.nextDouble();
        newWorkout.addExercise(new CardioExercise(name, duration, distance));
        System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " Added!");
    }

    // EFFECTS: Prints out list of commands for user
    private void showCommands() {
        System.out.println("Commands:");
        System.out.println("/makeWorkout");
        System.out.println("/viewWorkouts");
        System.out.println("/statsToday");
        System.out.println("/statsLast7");
        System.out.println("/quit");
    }

    // EFFECTS: Prints out all commands to interact with workout
    private void viewSelectedWorkoutCommands() {
        System.out.println("COMMANDS:");
        System.out.println("/setComplete");
        System.out.println("/setDate");
        System.out.println("/addExercise");
        System.out.println("/deleteExercise");
        System.out.println("/menu");
    }

    // EFFECTS: Prints out list of add commands
    private void showAddCommands() {
        System.out.println("Now add any number of exercises by using the following commands");
        System.out.println("/addCardioExercise");
        System.out.println("/addWeightLiftExercise");
        System.out.println("/addBodyWeightExercise");
        System.out.println("/addSport");
        System.out.println("/menu");
    }

}
