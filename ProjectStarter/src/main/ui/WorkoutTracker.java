package ui;

import model.Exercise;
import model.Goal;
import model.GoalsList;
import model.Workout;
import model.BestExerciseLog;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WorkoutTracker {
    private static final String WORKOUT_FILE = "./data/currentWorkout.json";
    private static final String BEST_EXERCISE_LOG_FILE = "./data/bestExerciseLog.json";
    private static final String GOALS_LIST_FILE = "./data/goalsList.json";

    private Workout currentWorkout;
    private BestExerciseLog bestExerciseLog;
    private GoalsList goalsList;
    private Scanner scanner;
    private boolean isProgramRunning;
    private JsonWriter workoutWriter;
    private JsonWriter bestExerciseLogWriter;
    private JsonWriter goalsListWriter;
    private JsonReader workoutReader;
    private JsonReader bestExerciseLogReader;
    private JsonReader goalsListReader;

    // EFFECTS: creates an instance of the WorkoutTracker console UI application
    public WorkoutTracker() throws FileNotFoundException {
        initialize();
        System.out.println("Get ready to enhance your fitness journey with FitJourney!");

        while (this.isProgramRunning) {
            handleMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the FitJourney with the logs and file readers/writers
    private void initialize() {
        this.currentWorkout = new Workout();
        this.bestExerciseLog = new BestExerciseLog();
        this.goalsList = new GoalsList();
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;

        this.workoutWriter = new JsonWriter(WORKOUT_FILE);
        this.bestExerciseLogWriter = new JsonWriter(BEST_EXERCISE_LOG_FILE);
        this.goalsListWriter = new JsonWriter(GOALS_LIST_FILE);
        this.workoutReader = new JsonReader(WORKOUT_FILE);
        this.bestExerciseLogReader = new JsonReader(BEST_EXERCISE_LOG_FILE);
        this.goalsListReader = new JsonReader(GOALS_LIST_FILE);
    }

    // EFFECTS: shows options and processes inputs in the main menu
    private void handleMenu() {
        showOptions();
        String input = this.scanner.nextLine();
        runInputs(input);
    }

    // EFFECTS: shows list of commands you can do in application(FitJourney)
    private void showOptions() {
        System.out.println("Please select an option:\n");
        System.out.println("1: Add a new exercise to the current workout");
        System.out.println("2: Remove an exercise from the current workout");
        System.out.println("3: Add a goal to goal log");
        System.out.println("4: View all exercises in the current workout");
        System.out.println("5: View best weights for all exercises");
        System.out.println("6: View your goals");
        System.out.println("7: Save data to file");
        System.out.println("8: Load data from file");
        System.out.println("9: Exit the application");
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input in the main menu
    // Code partially used from FlashcardReviewer
    @SuppressWarnings("methodlength")
    private void runInputs(String input) {
        switch (input) {
            case "1":
                addNewExercise();
                break;
            case "2":
                removeExercise();
                break;
            case "3":
                addGoal();
                break;
            case "4":
                viewExercises();
                break;
            case "5":
                viewBestExercises();
                break;
            case "6":
                viewGoals();
                break;
            case "7":
                saveData();
                break;
            case "8":
                loadData();
                break;
            case "9":
                quitApplication();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an exercise to the current workout
    private void addNewExercise() {
        System.out.println("Enter the exercise's name:");
        String name = this.scanner.nextLine();

        System.out.println("Enter the number of sets you will do:");
        Integer sets = Integer.parseInt(this.scanner.nextLine());

        System.out.println("Enter the number of reps per set you will do:");
        Integer reps = Integer.parseInt(this.scanner.nextLine());

        System.out.println("Enter the weight used:");
        Integer weight = Integer.parseInt(this.scanner.nextLine());

        Exercise exercise = new Exercise(name, sets, reps, weight);

        this.currentWorkout.addExercise(exercise);
        this.bestExerciseLog.addPrExercise(exercise);
        System.out.println("The exercise has been added to your current workout and personal record log!");
    }

    // MODIFIES: this
    // EFFECTS: removes an exercise from the current workout
    private void removeExercise() {
        System.out.println("Please enter the name of the exercise to remove:");
        String name = this.scanner.nextLine();

        List<Exercise> exercises = this.currentWorkout.getExercises();

        for (Exercise exercise : exercises) {
            if (exercise.getName().equalsIgnoreCase(name)) {
                this.currentWorkout.removeExercise(exercise);
                System.out.println("Exercise " + name + " has been removed from your current workout.");
                return;
            }
        }
        System.out.println("Exercise " + name + " not found in your current workout.");
    }

    // MODIFIES: this
    // EFFECTS: adds a goal to the goals list
    private void addGoal() {
        System.out.println("Enter the exercise's name for the goal:");
        String name = this.scanner.nextLine();

        System.out.println("Enter the target weight for the goal:");
        Integer targetWeight = Integer.parseInt(this.scanner.nextLine());

        Goal goal = new Goal(name, targetWeight);
        goalsList.addGoalToList(goal);
        System.out.println("Goal added successfully!");
    }

    // EFFECTS: displays all exercises in the current workout
    private void viewExercises() {
        List<Exercise> exercises = this.currentWorkout.getExercises();
        if (exercises.isEmpty()) {
            System.out.println("There are no exercises in your current workout.");
        } else {
            for (Exercise exercise : exercises) {
                System.out.println("Name: " + exercise.getName() + "\n"
                        + "Sets: " + exercise.getSets() + "\n"
                        + "Reps: " + exercise.getReps() + "\n"
                        + "Weight: " + exercise.getWeight());
            }
        }
    }

    // EFFECTS: displays the best weights for all exercises
    private void viewBestExercises() {
        List<Exercise> bestExercises = this.bestExerciseLog.getAllExercises();
        if (bestExercises.isEmpty()) {
            System.out.println("No exercises recorded yet.");
        } else {
            for (Exercise exercise : bestExercises) {
                System.out.println("Name: " + exercise.getName() + "\n"
                        + "Best Weight: " + exercise.getWeight());
            }
        }
    }

    // EFFECTS: displays all goals
    private void viewGoals() {
        List<Goal> goals = this.goalsList.getGoalsList();
        if (goals.isEmpty()) {
            System.out.println("No goals set yet.");
        } else {
            System.out.println("Goals:");
            for (Goal goal : goals) {
                System.out.println("Exercise: " + goal.getExerciseName()
                        + ", Target Weight: " + goal.getTargetWeight());
            }
        }
    }

    // EFFECTS: saves data to files
    private void saveData() {
        try {
            workoutWriter.open();
            workoutWriter.writeWorkout(currentWorkout); // Save only the current workout
            workoutWriter.close();
            System.out.println("Saved current workout data to " + WORKOUT_FILE);

            bestExerciseLogWriter.open();
            bestExerciseLogWriter.writeExerciseLog(bestExerciseLog);
            bestExerciseLogWriter.close();
            System.out.println("Saved best exercise log data to " + BEST_EXERCISE_LOG_FILE);

            goalsListWriter.open();
            goalsListWriter.writeGoallist(goalsList);
            goalsListWriter.close();
            System.out.println("Saved goals list data to " + GOALS_LIST_FILE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to save data to file: " + e.getMessage());
        }
    }

    // EFFECTS: loads data from files
    private void loadData() {
        try {
            currentWorkout = workoutReader.readCurrentWorkout(); // Load only the current workout
            System.out.println("Loaded current workout data from " + WORKOUT_FILE);

            bestExerciseLog = bestExerciseLogReader.readBestExerciseLog();
            System.out.println("Loaded best exercise log data from " + BEST_EXERCISE_LOG_FILE);

            goalsList = goalsListReader.readGoalsList();
            System.out.println("Loaded goals list data from " + GOALS_LIST_FILE);

        } catch (IOException e) {
            System.out.println("Unable to read data from file: " + e.getMessage());
        }
    }

    // EFFECTS: prints a closing message and marks the program as not running
    private void quitApplication() {
        System.out.println("Thank you for using FitJourney! See you next time.");
        printLog();
        this.isProgramRunning = false;
    }

    private void printLog() {
        System.out.println("Event Log:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }
}