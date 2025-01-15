package ui;

import model.Exercise;
import model.Goal;
import model.BestExerciseLog;
import model.EventLog;
import model.GoalsList;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Event;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// WorkoutTrackerGUI provides a graphical user interface for tracking workouts, goals, and personal records.

public class WorkoutTrackerGUI extends JFrame {
    private static final String WORKOUT_FILE = "./data/currentWorkout.json";
    private static final String BEST_EXERCISE_LOG_FILE = "./data/bestExerciseLog.json";
    private static final String GOALS_LIST_FILE = "./data/goalsList.json";

    private Workout currentWorkout;
    private BestExerciseLog bestExerciseLog;
    private GoalsList goalsList;

    private JTextArea exerciseArea;
    private JTextArea goalArea;
    private JTextArea bestExercisesArea;

    private JsonWriter workoutWriter;
    private JsonWriter bestExerciseLogWriter;
    private JsonWriter goalsListWriter;
    private JsonReader workoutReader;
    private JsonReader bestExerciseLogReader;
    private JsonReader goalsListReader;

    // MODIFIES: this
    // EFFECTS: Initializes the GUI and its components
    public WorkoutTrackerGUI() {
        setTitle("Workout Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initialize();
        setupPanels();
        displayEventLog();

        System.out.println("WorkoutTrackerGUI initialized");

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the workout, best exercise log, goals list, and JSON
    // readers/writers
    private void initialize() {
        runSplashScreen();
        currentWorkout = new Workout();
        bestExerciseLog = new BestExerciseLog();
        goalsList = new GoalsList();

        workoutWriter = new JsonWriter(WORKOUT_FILE);
        bestExerciseLogWriter = new JsonWriter(BEST_EXERCISE_LOG_FILE);
        goalsListWriter = new JsonWriter(GOALS_LIST_FILE);
        workoutReader = new JsonReader(WORKOUT_FILE);
        bestExerciseLogReader = new JsonReader(BEST_EXERCISE_LOG_FILE);
        goalsListReader = new JsonReader(GOALS_LIST_FILE);
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: Sets up the panels for app
    private void setupPanels() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1));

        // Panel for exercises
        JPanel exercisePanel = new JPanel();
        exercisePanel.setLayout(new BorderLayout());
        exerciseArea = new JTextArea();
        exerciseArea.setEditable(false);
        JScrollPane exerciseScroll = new JScrollPane(exerciseArea);
        JButton addExerciseButton = new JButton("Add Exercise");
        JButton removeExerciseButton = new JButton("Remove Exercise");

        addExerciseButton.addActionListener(e -> addExercise());
        removeExerciseButton.addActionListener(e -> removeExercise());

        exercisePanel.add(exerciseScroll, BorderLayout.CENTER);
        JPanel exerciseButtonPanel = new JPanel();
        exerciseButtonPanel.add(addExerciseButton);
        exerciseButtonPanel.add(removeExerciseButton);
        exercisePanel.add(exerciseButtonPanel, BorderLayout.SOUTH);

        // Panel for goals
        JPanel goalPanel = new JPanel();
        goalPanel.setLayout(new BorderLayout());
        goalArea = new JTextArea();
        goalArea.setEditable(false);
        JScrollPane goalScroll = new JScrollPane(goalArea);
        JButton addGoalButton = new JButton("Add Goal");

        addGoalButton.addActionListener(e -> addGoal());

        goalPanel.add(goalScroll, BorderLayout.CENTER);
        JPanel goalButtonPanel = new JPanel();
        goalButtonPanel.add(addGoalButton);
        goalPanel.add(goalButtonPanel, BorderLayout.SOUTH);

        // Panel for best exercises
        JPanel bestExercisesPanel = new JPanel();
        bestExercisesPanel.setLayout(new BorderLayout());
        bestExercisesArea = new JTextArea();
        bestExercisesArea.setEditable(false);
        JScrollPane bestExercisesScroll = new JScrollPane(bestExercisesArea);
        JButton refreshBestExercisesButton = new JButton("Refresh Best Exercises");

        refreshBestExercisesButton.addActionListener(e -> updateBestExercisesArea());

        bestExercisesPanel.add(bestExercisesScroll, BorderLayout.CENTER);
        JPanel bestExercisesButtonPanel = new JPanel();
        bestExercisesButtonPanel.add(refreshBestExercisesButton);
        bestExercisesPanel.add(bestExercisesButtonPanel, BorderLayout.SOUTH);

        // Panel for save/load buttons
        JPanel saveLoadPanel = new JPanel();
        JButton saveButton = new JButton("Save Data");
        JButton loadButton = new JButton("Load Data");

        saveButton.addActionListener(e -> saveData());
        loadButton.addActionListener(e -> {
            try {
                loadData();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
            }
        });

        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(loadButton);

        centerPanel.add(exercisePanel);
        centerPanel.add(goalPanel);
        centerPanel.add(bestExercisesPanel);
        centerPanel.add(saveLoadPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Displays a splash screen with an image for 7 seconds
    private void runSplashScreen() {
        JLabel imageLabel = new JLabel(new ImageIcon("./data/SplashScreen.png"));
        JFrame splashScreen = new JFrame();
        splashScreen.add(imageLabel);
        splashScreen.setSize(imageLabel.getPreferredSize());
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println(e);
        }
        splashScreen.dispose();
    }

    // REQUIRES: User input for exercise name, sets, reps, and weight
    // MODIFIES: this, currentWorkout, bestExerciseLog
    // EFFECTS: Adds exercise to workout and updates the best exercise log
    private void addExercise() {
        String name = JOptionPane.showInputDialog(this, "Enter exercise name:");
        int sets = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of sets:"));
        int reps = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of reps:"));
        int weight = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter weight used:"));

        Exercise exercise = new Exercise(name, sets, reps, weight);
        currentWorkout.addExercise(exercise);
        bestExerciseLog.addPrExercise(exercise);
        updateExerciseArea();
    }

    // REQUIRES: User input for exercise name to remove
    // MODIFIES: this, currentWorkout
    // EFFECTS: Removes an exercise from the current workout if it exists
    private void removeExercise() {
        String name = JOptionPane.showInputDialog(this, "Enter exercise name to remove:");
        List<Exercise> exercises = currentWorkout.getExercises();

        for (Exercise exercise : exercises) {
            if (exercise.getName().equalsIgnoreCase(name)) {
                currentWorkout.removeExercise(exercise);
                updateExerciseArea();
                JOptionPane.showMessageDialog(this, "Exercise removed.");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Exercise not found.");
    }

    // REQUIRES: User input for exercise name and target weight
    // MODIFIES: this, goalsList
    // EFFECTS: Adds a new goal to the goals list
    private void addGoal() {
        String name = JOptionPane.showInputDialog(this, "Enter exercise name for goal:");
        int targetWeight = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter target weight:"));

        Goal goal = new Goal(name, targetWeight);
        goalsList.addGoalToList(goal);
        updateGoalArea();
    }

    // MODIFIES: this
    // EFFECTS: Updates the exercise area to display current exercises
    private void updateExerciseArea() {
        if (exerciseArea == null) {
            System.out.println("exerciseArea is null");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Exercise exercise : currentWorkout.getExercises()) {
            sb.append("Name: ").append(exercise.getName()).append("\n")
                    .append("Sets: ").append(exercise.getSets()).append("\n")
                    .append("Reps: ").append(exercise.getReps()).append("\n")
                    .append("Weight: ").append(exercise.getWeight()).append("\n\n");
        }
        exerciseArea.setText(sb.toString());
    }

    // MODIFIES: this
    // EFFECTS: Updates best exercises area to display current best exercises
    private void updateBestExercisesArea() {
        if (bestExercisesArea == null) {
            System.out.println("bestExercisesArea is null");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Exercise exercise : bestExerciseLog.getAllExercises()) {
            sb.append("Name: ").append(exercise.getName()).append("\n")
                    .append("Sets: ").append(exercise.getSets()).append("\n")
                    .append("Reps: ").append(exercise.getReps()).append("\n")
                    .append("Weight: ").append(exercise.getWeight()).append("\n\n");
        }
        bestExercisesArea.setText(sb.toString());
    }

    // MODIFIES: this
    // EFFECTS: Updates the goal area to display current goals
    private void updateGoalArea() {
        if (goalArea == null) {
            System.out.println("goalArea is null");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Goal goal : goalsList.getGoalsList()) {
            sb.append("Exercise: ").append(goal.getExerciseName()).append("\n")
                    .append("Target Weight: ").append(goal.getTargetWeight()).append("\n\n");
        }
        goalArea.setText(sb.toString());
    }

    // EFFECTS: Saves current workout, best exercise log, goals list to files
    private void saveData() {
        try {
            workoutWriter.open();
            workoutWriter.writeWorkout(currentWorkout);
            workoutWriter.close();

            bestExerciseLogWriter.open();
            bestExerciseLogWriter.writeExerciseLog(bestExerciseLog);
            bestExerciseLogWriter.close();

            goalsListWriter.open();
            goalsListWriter.writeGoallist(goalsList);
            goalsListWriter.close();

            JOptionPane.showMessageDialog(this, "Data saved successfully.");

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    // EFFECTS: Loads current workout, best exercise log, goals list from files
    private void loadData() throws IOException {
        try {
            currentWorkout = workoutReader.readCurrentWorkout();
            bestExerciseLog = bestExerciseLogReader.readBestExerciseLog();
            goalsList = goalsListReader.readGoalsList();

            updateExerciseArea();
            updateGoalArea();
            updateBestExercisesArea();

        } catch (IOException e) {
            throw new IOException("Error loading data: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a window listener that prints the event log when the window
    // closes
    private void displayEventLog() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
            }
        });
    }

    // EFFECTS: Prints the event log to the console
    private void printLog() {
        System.out.println("Event Log:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }

}
