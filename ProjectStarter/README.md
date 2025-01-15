# My Personal Project (Gym Workout Tracker)

## What will it do?

The *Gym Workout Tracker* is a Java-based application designed to help users efficiently track their gym workouts and add their own exercises to their workout. The application allows users to log their workouts, including **exercises performed**, **sets**, **reps**, and **weights used**. It also offers a curated list of exercises tailored to the user's fitness goals, experience level. Users can set goals and view progress over time. The app includes features such as workout templates and progress charts.

This application is intended for **fitness enthusiasts**, from **beginners** to **advanced gym-goers**, who want to organize their workout routines and track their progress. Personal trainers may also find it useful for managing their clients' workout plans and monitoring their progress. The Gym Workout Tracker can benefit anyone looking to maintain consistency in their fitness regimen and achieve their fitness goals more effectively.

#### Personal Interest

This project is of particular interest to me because of my passion for fitness and technology. By combining these interests, I can create a tool that not only enhances my own workout experience but also helps others in their fitness journeys. Developing this application will also provide me with valuable experience in Java programming, user interface design which are crucial skills in software development while also allowing me to reach my fitness goals.


## User Stories

As a user, I want to add exercises to a workout so I can customize what muscle groups I want to hit

As a user, I want to view a list of all the exercises that I have added to my workout 

As a user, I want to track my personal bests for different exercises so that I can monitor my improvements.

As a user, I want to set workout goals so that I can stay motivated and track my progress towards specific targets.

As a user, I want to be able to save my workout that I created during that session

As a user, when I start the application I want to be able to load past workouts that I have saved


# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the "Add Exercise" button located in the "Exercises" section of the main panel. A dialog will prompt you to enter the details of the exercise, and upon confirmation, it will be added to the current workout.

- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "Remove Exercise" button located in the "Exercises" section of the main panel. A dialog will prompt you to enter the name of the exercise, and upon confirmation, it will be removed from the exercises list.

- You can locate my visual component by observing the splash screen that displays an image when the application starts. The image file used for the splash screen is located at `./data/SplashScreen.png`.

- You can save the state of my application by clicking the "Save Data" button located in the bottom panel of the main GUI. This will save the current workout, best exercise log, and goals list to their respective JSON files.

- You can reload the state of my application by clicking the "Load Data" button located in the bottom panel of the main GUI. This will load the workout, best exercise log, and goals list from their respective JSON files.


## Phase 4: Task 2 (EventLog sample)
Event Log:
Thu Aug 08 12:00:19 PDT 2024
Loaded current workout from ./data/currentWorkout.json
Thu Aug 08 12:00:19 PDT 2024
Loaded BestExerciseLog from ./data/bestExerciseLog.json
Thu Aug 08 12:00:19 PDT 2024
Loaded GoalsList from ./data/goalsList.json
Thu Aug 08 12:00:33 PDT 2024
Added exercise to workout: squat
Thu Aug 08 12:00:33 PDT 2024
Added new PR exercise: squat with weight 100
Thu Aug 08 12:00:38 PDT 2024
Added goal: bench with target weight 100
Thu Aug 08 12:00:41 PDT 2024
Removed exercise from workout: squat
Thu Aug 08 12:00:44 PDT 2024
Saved Workout to ./data/currentWorkout.json
Thu Aug 08 12:00:44 PDT 2024
Saved BestExerciseLog to ./data/bestExerciseLog.json
Thu Aug 08 12:00:44 PDT 2024
Saved GoalsList to ./data/goalsList.json



## Phase 4: Task 3

The current design works but could be improved by incorporating abstraction to handle exercise types more effectively. Currently the Exercise class is too general, leading to generality when managing exercises with different attributes, like running (which has time, duration, and distance) and bench press (which has sets and reps). Refactoring to include an Exercise abstraction would allow me to handle different exercise types, allowing for more precise management and calculation of statistics, and preventing issues with irrelevant attributes. To do this I would make Exercise an abstract class and then extend a bunch of sub classes of types of exercises.



## Citations:
FlashcardReviewer - https://us.prairielearn.com/pl/course_instance/155647/instance_question/392305018/
JsonSerializationDemo - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo \
Java Swing Syntax - https://www.youtube.com/watch?v=5o3fMLPY7qY \
Java Swing Tutorial - https://www.javatpoint.com/java-swing 
