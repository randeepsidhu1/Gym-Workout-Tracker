package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import model.Event;
import model.EventLog;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

// Represents a GUI for the Workout Wizard App
public class GUI {
    private static final String JSON_DEST = "./data/userworkouts.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private UserWorkouts userWorkouts;
    private JFrame frame;
    private JPanel mainPanel;

    private JLabel workoutsLabel;
    private JPanel workoutsPanel;
    private JScrollPane workoutsPane;
    private JButton createWorkoutButton;
    private JButton removeWorkoutButton;
    private JPanel workoutButtonsPanel;

    private JLabel exercisesLabel;
    private JPanel exercisesPanel;
    private JScrollPane exercisesPane;
    private JButton addExerciseButton;
    private JButton removeExerciseButton;
    private JPanel exerciseButtonsPanel;



    private JList<WorkoutRoutine> workoutsJList;
    private DefaultListModel<WorkoutRoutine> workoutsListModel;
    private JList<Exercise> exerciseJList;
    private DefaultListModel<Exercise> exerciseListModel;

    // MODIFIES: this
    // EFFECTS: Constructs the GUI
    public GUI() {
        runSplashScreen();
        frame = new JFrame();
        mainPanel = new JPanel(new GridLayout(1, 3));
        workoutsListModel = new DefaultListModel<>();
        workoutsJList = new JList<>(workoutsListModel);
        workoutsJList.setCellRenderer(new WorkoutsRenderer());
        exerciseListModel = new DefaultListModel<>();
        exerciseJList = new JList<>(exerciseListModel);
        exerciseJList.setCellRenderer(new ExerciseRenderer());
        jsonWriter = new JsonWriter(JSON_DEST);
        jsonReader = new JsonReader(JSON_DEST);
        userWorkouts = new UserWorkouts();

        askUserToLoad();

        initializeFrame();
        initializeWorkoutsPanel();
        initializeExercisesPanel();

        frame.setVisible(true);
    }

    // EFFECTS: Prints each event time and description
    private void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: allows workout to be removed by pressing remove workout button
    private void initializeRemoveWorkoutButton() {
        removeWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = workoutsJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    userWorkouts.removeWorkoutRoutine(workoutsJList.getSelectedValue());
                    refreshWorkouts();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: opens a pop-up to create a workout when button is clicked.
    private void initializeCreateWorkoutButton() {
        createWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addWorkoutFrame = new JFrame("Create Workout");
                JPanel addWorkoutPanel = new JPanel(new BorderLayout());
                JTextField dateBox = new JTextField("YYYY-MM-DD");
                JButton addButton = new JButton("Add");
                JButton cancelButton = new JButton("Cancel");
                addWorkoutFrame.setSize(350, 250);
                addWorkoutFrame.setResizable(false);
                addWorkoutFrame.add(addWorkoutPanel);
                addWorkoutPanel.add(dateBox, BorderLayout.NORTH);
                addWorkoutPopUpButtonFunctionality(addWorkoutFrame, dateBox, addButton, cancelButton);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(addButton);
                buttonPanel.add(cancelButton);
                addWorkoutPanel.add(buttonPanel, BorderLayout.CENTER);
                addWorkoutFrame.setVisible(true);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds functionality for create new workout popup
    private void addWorkoutPopUpButtonFunctionality(JFrame addWorkoutFrame, JTextField dateBox, JButton addButton,
                                                    JButton cancelButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userWorkouts.addWorkoutRoutine(new WorkoutRoutine(LocalDate.parse(dateBox.getText())));
                refreshWorkouts();
                addWorkoutFrame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWorkoutFrame.dispose();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Opens a yes or no prompt, and loads saved workouts if yes.
    private void askUserToLoad() {
        int loadExercises = JOptionPane.showConfirmDialog(null, "Do you want to load saved Workouts?",
                "Load Workouts", JOptionPane.YES_NO_OPTION);
        if (loadExercises == JOptionPane.YES_OPTION) {
            try {
                userWorkouts = jsonReader.read();
                refreshWorkouts();
            } catch (IOException e) {
                System.out.println("Unable to read file: " + JSON_DEST);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Updates the list of workouts, exercises, and details
    private void refreshWorkouts() {
        workoutsListModel.clear();
        ArrayList<WorkoutRoutine> workoutList = userWorkouts.getWorkouts();
        for (WorkoutRoutine workoutRoutine : workoutList) {
            workoutsListModel.addElement(workoutRoutine);
        }
        refreshExercises();
    }

    // MODIFIES: this
    // EFFECTS: Updates the list of exercises
    private void refreshExercises() {
        exerciseListModel.clear();
        WorkoutRoutine selectedRoutine = workoutsJList.getSelectedValue();
        if (selectedRoutine != null) {
            ArrayList<Exercise> exerciseList = selectedRoutine.getExercises();
            for (Exercise exercise : exerciseList) {
                exerciseListModel.addElement(exercise);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes Applications main JFrame
    private void initializeFrame() {
        frame.setTitle("Workout Wizard");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeSavePrompt();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(mainPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a listener to prompt the user to save when app is closed
    private void initializeSavePrompt() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Do you want to save your workouts?", "Save Workouts", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.openFile();
                        jsonWriter.write(userWorkouts);
                        jsonWriter.close();
                    } catch (FileNotFoundException fileNotFoundException) {
                        System.out.println("Unable to write to file: " + JSON_DEST);
                    }
                }
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Creates the Workout Panel
    private void initializeWorkoutsPanel() {
        workoutsLabel = new JLabel("Workouts", SwingConstants.CENTER);
        workoutsPanel = new JPanel(new BorderLayout());
        workoutsPane = new JScrollPane(workoutsJList);
        createWorkoutButton = new JButton("Create Workout");
        removeWorkoutButton = new JButton("Remove Workout");
        workoutsPanel.add(workoutsLabel, BorderLayout.NORTH);
        workoutsPanel.add(workoutsPane, BorderLayout.CENTER);
        workoutButtonsPanel = new JPanel(new GridLayout(1, 2));
        workoutButtonsPanel.add(createWorkoutButton);
        workoutButtonsPanel.add(removeWorkoutButton);
        workoutsPanel.add(workoutButtonsPanel, BorderLayout.SOUTH);
        mainPanel.add(workoutsPanel);
        initializeCreateWorkoutButton();
        initializeRemoveWorkoutButton();
        initializeWorkoutSelection();
    }

    // MODIFIES: this
    // EFFECTS: Adds listener to workouts JList
    private void initializeWorkoutSelection() {
        workoutsJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    refreshExercises();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Creates the exercises panel
    private void initializeExercisesPanel() {
        exercisesLabel = new JLabel("Exercises", SwingConstants.CENTER);
        exercisesPanel = new JPanel(new BorderLayout());
        exercisesPane = new JScrollPane(exerciseJList);
        addExerciseButton = new JButton("Add Exercise");
        removeExerciseButton = new JButton("Remove Exercise");
        exercisesPanel.add(exercisesLabel, BorderLayout.NORTH);
        exercisesPanel.add(exercisesPane, BorderLayout.CENTER);
        exerciseButtonsPanel = new JPanel(new GridLayout(1, 2));
        exerciseButtonsPanel.add(addExerciseButton);
        exerciseButtonsPanel.add(removeExerciseButton);
        exercisesPanel.add(exerciseButtonsPanel, BorderLayout.SOUTH);
        mainPanel.add(exercisesPanel);
        initializeAddExerciseButton();
        initializeRemoveExerciseButton();
    }

    // MODIFIES: this
    // EFFECTS: Creates functionality for add exercise button
    private void initializeAddExerciseButton() {
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addExerciseFrame = new JFrame("Create Exercise");
                JPanel addExercisePanel = new JPanel(new GridLayout(4, 1));
                JButton addCardio = new JButton("Add Cardio");
                JButton addSport = new JButton("Add Sport");
                JButton addWeightLift = new JButton("Add Weightlifting");
                JButton addBodyWeight = new JButton("Add Bodyweight Exercise");
                addExerciseFrame.setSize(300, 600);
                addExerciseFrame.setResizable(false);
                addExerciseFrame.add(addExercisePanel);
                addExercisePanel.add(addCardio);
                addExercisePanel.add(addSport);
                addExercisePanel.add(addWeightLift);
                addExercisePanel.add(addBodyWeight);

                initializeAddCardioButton(addExerciseFrame, addCardio);
                initializeAddSportButton(addExerciseFrame, addSport);
                initializeAddWeightLiftExerciseButton(addExerciseFrame, addWeightLift);
                initializeAddBodyWeightExerciseButton(addExerciseFrame, addBodyWeight);
                addExerciseFrame.setVisible(true);
            }
        });
    }

    // MODIFIES: this, addCardio, addExerciseFrame
    // EFFECTS: Creates pop up window to add cardio exercise
    private void initializeAddCardioButton(JFrame addExerciseFrame, JButton addCardio) {
        addCardio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addCardioFrame = new JFrame("Add Cardio Exercise");
                JPanel addCardioPanel = new JPanel(new GridLayout(4, 1));
                JTextField name = new JTextField("Running, Biking, Walking, or Swimming");
                JTextField duration = new JTextField("Duration in mins");
                JTextField distance = new JTextField("Distance in KM");
                JButton addExerciseButton = new JButton("Add Exercise");
                addCardioFrame.setSize(350, 400);
                addCardioFrame.setResizable(false);
                createAddCardioButtonFunctionality(addCardioFrame, name, duration, distance, addExerciseButton);
                packAddExerciseFrame(addCardioFrame, addCardioPanel, name, duration, distance, addExerciseButton,
                        addExerciseFrame);
            }
        });
    }

    // MODIFIES: this, addExerciseButton
    // EFFECTS: Adds functionality to add cardio button in add cardio popup
    private void createAddCardioButtonFunctionality(JFrame addCardioFrame, JTextField name, JTextField duration,
                                                    JTextField distance, JButton addExerciseButton) {
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutsJList.getSelectedValue().addExercise(
                        new CardioExercise(name.getText(),
                                Integer.valueOf(duration.getText()),
                                Double.valueOf(distance.getText())));
                refreshExercises();
                addCardioFrame.dispose();
            }
        });
    }

    // MODIFIES: this, addSport, addExerciseFrame
    // EFFECTS: Creates pop up window to add sport exercises
    private void initializeAddSportButton(JFrame addExerciseFrame, JButton addSport) {
        addSport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addSportFrame = new JFrame("Add Sport");
                JPanel addSportPanel = new JPanel(new GridLayout(4, 1));
                JTextField name = new JTextField("Basketball, Soccer, Hockey, Baseball");
                JTextField duration = new JTextField("Duration in mins");
                JTextField pace = new JTextField("Pace (Light, Average, Intense)");
                JButton addExerciseButton = new JButton("Add Exercise");
                addSportFrame.setSize(350, 400);
                addSportFrame.setResizable(false);
                createAddSportButtonFunctionality(addSportFrame, name, duration, pace, addExerciseButton);
                packAddExerciseFrame(addSportFrame, addSportPanel, name, duration, pace, addExerciseButton,
                        addExerciseFrame);
            }
        });
    }

    // MODIFIES: this, addExerciseButton
    // EFFECTS: Adds functionality to add sport exercise button in add sport popup
    private void createAddSportButtonFunctionality(JFrame addSportFrame, JTextField name, JTextField duration,
                                                   JTextField pace, JButton addExerciseButton) {
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutsJList.getSelectedValue().addExercise(
                        new SportExercise(name.getText(),
                                Integer.valueOf(duration.getText()),
                                pace.getText()));
                refreshExercises();
                addSportFrame.dispose();
            }
        });
    }

    // MODIFIES: this, addSport, addWeightLift
    // EFFECTS: Creates pop up window to add weight lifting exercises
    private void initializeAddWeightLiftExerciseButton(JFrame addExerciseFrame, JButton addWeightLift) {
        addWeightLift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addWeightFrame = new JFrame("Add Weight Lifting Exercise");
                JPanel addWeightPanel = new JPanel(new GridLayout(4, 1));
                JTextField name = new JTextField("Squat, Legpress, Lunge, Deadlift, Bench, Shoulderpress, Dumbbells");
                JTextField sets = new JTextField("Sets");
                JTextField reps = new JTextField("Reps");
                JButton addExerciseButton = new JButton("Add Exercise");
                addWeightFrame.setSize(350, 400);
                addWeightFrame.setResizable(false);
                addWeightLiftExerciseButtonFunctionality(addWeightFrame, name, sets, reps, addExerciseButton);
                packAddExerciseFrame(addWeightFrame, addWeightPanel, name, sets, reps, addExerciseButton,
                        addExerciseFrame);
            }
        });
    }

    // EFFECTS: Runs a splash screen for 3 seconds
    private void runSplashScreen() {
        JLabel imageLabel = new JLabel(new ImageIcon("./data/SplashScreen.png"));
        JFrame splashScreen = new JFrame();
        splashScreen.add(imageLabel);
        splashScreen.setSize(imageLabel.getPreferredSize());
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e);
        }
        splashScreen.dispose();
    }

    // MODIFIES: this, addExerciseButton, addWeightFrame
    // EFFECTS: Adds functionality to add weight lift exercise button in add weight lift popup
    private void addWeightLiftExerciseButtonFunctionality(JFrame addWeightFrame, JTextField name, JTextField sets,
                                                          JTextField reps, JButton addExerciseButton) {
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutsJList.getSelectedValue().addExercise(
                        new WeightTrainingExercise(name.getText(),
                                Integer.valueOf(sets.getText()),
                                Integer.valueOf(reps.getText())));
                refreshExercises();
                addWeightFrame.dispose();
            }
        });
    }


    // MODIFIES: this, addExerciseButton, addBodyFrame
    // EFFECTS: Adds functionality to add body weight exercise button
    private void initializeAddBodyWeightExerciseButton(JFrame addExerciseFrame, JButton addBodyWeight) {
        addBodyWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addBodyFrame = new JFrame("Add Body Weight Exercise");
                JPanel addBodyPanel = new JPanel(new GridLayout(4, 1));
                JTextField name = new JTextField("Pushups, Pullups, Crunches, Jumpingjacks, Situps");
                JTextField sets = new JTextField("Sets");
                JTextField reps = new JTextField("Reps");
                JButton addExerciseButton = new JButton("Add Exercise");
                addBodyFrame.setSize(350, 400);
                addBodyFrame.setResizable(false);
                addBodyWeightExerciseButtonFunctionality(addBodyFrame, name, sets, reps, addExerciseButton);
                packAddExerciseFrame(addBodyFrame, addBodyPanel, name, sets, reps, addExerciseButton, addExerciseFrame);
            }
        });
    }

    // MODIFIES: this, addExerciseButton, addBodyFrame
    // EFFECTS: Adds functionality to add exercise button
    private void addBodyWeightExerciseButtonFunctionality(JFrame addBodyFrame, JTextField name, JTextField sets,
                                                          JTextField reps, JButton addExerciseButton) {
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutsJList.getSelectedValue().addExercise(
                        new BodyWeightExercise(name.getText(),
                                Integer.valueOf(sets.getText()),
                                Integer.valueOf(reps.getText())));
                refreshExercises();
                addBodyFrame.dispose();
            }
        });
    }

    // MODIFIES: this, addBodyPanel, addBodyFrame, addExerciseFrame
    // EFFECTS: Adds all elements to add exercise frame
    private static void packAddExerciseFrame(JFrame addBodyFrame, JPanel addBodyPanel, JTextField name, JTextField sets,
                                             JTextField reps, JButton addExerciseButton, JFrame addExerciseFrame) {
        addBodyPanel.add(name);
        addBodyPanel.add(sets);
        addBodyPanel.add(reps);
        addBodyPanel.add(addExerciseButton);
        addBodyFrame.add(addBodyPanel);
        addExerciseFrame.dispose();
        addBodyFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates functionality for exercise remove button
    private void initializeRemoveExerciseButton() {
        removeExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = exerciseJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    workoutsJList.getSelectedValue().removeExercise(exerciseJList.getSelectedValue());
                    refreshExercises();
                }
            }
        });
    }

    // A class that changes the styling of the labels in the Workout JList
    private static class WorkoutsRenderer extends JLabel implements ListCellRenderer<WorkoutRoutine> {
        // MODIFIES: this
        // EFFECTS: Constructs Workouts Renderer
        public WorkoutsRenderer() {
            setOpaque(true);
            setBorder(new EmptyBorder(5, 8, 5, 8));
        }

        // MODIFIES: this
        // EFFECTS: Changes default cell text
        @Override
        public Component getListCellRendererComponent(JList<? extends WorkoutRoutine> list, WorkoutRoutine value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.getDate().toString());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }

    // A class that changes the styling of the labels in the Exercise JList
    private static class ExerciseRenderer extends JLabel implements ListCellRenderer<Exercise> {

        // MODIFIES: this
        // EFFECTS: Constructs Exercise Renderer
        public ExerciseRenderer() {
            setOpaque(true);
            setBorder(new EmptyBorder(5, 8, 5, 8));
        }

        // MODIFIES: this
        // EFFECTS: Changes default cell text
        @Override
        public Component getListCellRendererComponent(JList<? extends Exercise> list, Exercise value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.getName().substring(0, 1).toUpperCase() + value.getName().substring(1)
                    + ": " + value.getStats());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }

}
