`# WorkoutWizard

### A program that allows you to plan, track, and analyze your workouts.

**WorkoutWizard** is a program designed to allow individuals
to browse, or create custom workout routines. The program
saves workout history, and offers many different features
such as planning workouts, checking off exercises *mid
workout*, and an analysis of fitness sessions. This program
will be of use to anyone who is interested in getting
healthier. Since there are custom workout routines, and 
also pre-made routines to browse, anyone is able to use
this program to assist in their fitness journey. I have
been playing sports for my entire childhood, and therefore
I recognize the importance of leading a healthy lifestyle
to ensure balance and well-being. I want to be able to
allow others to better themselves in easy ways, and this
program has the capability of doing this.


### User Stories:
- As a user, I want to be able to create a workout routine.
- As a user, I want to be able to add exercises to workout routines.
- As a user, I want to be able to remove exercises to workout routines.
- As a user, I want to be able to select a saved workout routine and view exercises in that workout routine.
- As a user, I want to be able to select a previous workout routine, and view details about that routine, including which exercises were in that routine and analysis.
- As a user, I want to be able to plan a workout routine for any date
- As a user, I want to be able to select a time period (today, last 7 days) and see statistics of the workout routines completed in that time frame.
- As a user, when I select the quit option from the application menu, I want to be reminded to save my to-do list to file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my to-do list from file.


### Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by
clicking create workout which adds a WorkoutRoutine to UserWorkouts, or by clicking create exercise which
adds an Exercise to a WorkoutRoutine.
- You can generate the second required action related to the user story "removing Xs from a Y" by
clicking remove workout which removes a WorkoutRoutine from UserWorkouts, or by clicking remove exercise
which removes an Exercise from a WorkoutRoutine.
- You can locate my visual component by running the application (there is a splash screen).
- You can save the state of my application by selecting yes when prompted when closing the application.
- You can load the state of my application by selecting yes when prompted when opening the application.


### Short Tutorial on WorkoutWizard's features
- Click the button labelled "Create Workout" to create a new workout and add it to user's workouts
- A panel with all the user's workouts appears on the left hand side of the application, select
a workout to view its exercises on the pane on the right.
- Click the button labelled "Add Exercise" to add a new exercise to currently selected workout
- Click the button labelled "Remove Workout" to delete whichever workout is selected
- Click the button labelled "Remove Exercise" to delete whichever exercise is selected
- The visual component is the splash screen which runs every time the app launches!
- The user can save the state of the application from file when closing the app
- The user can load the state of the application from file when opening the app

### Phase 4: Task 2
Sample of EventLog after use: \
Thu Apr 04 19:12:54 PDT 2024 \
Saved workouts read from file \
Thu Apr 04 19:12:54 PDT 2024 \
Exercise `biking` added to Workout on 2024-03-06 \
Thu Apr 04 19:12:54 PDT 2024 \
Exercise `crunches` added to Workout on 2024-03-06 \
Thu Apr 04 19:12:54 PDT 2024 \
Exercise `soccer` added to Workout on 2024-03-06 \
Thu Apr 04 19:12:54 PDT 2024 \
Exercise `situps` added to Workout on 2024-03-06 \
Thu Apr 04 19:12:54 PDT 2024 \
New Workout created on 2024-03-06 \
Thu Apr 04 19:12:54 PDT 2024 \
Exercise `deadlift` added to Workout on 2024-03-14 \
Thu Apr 04 19:12:54 PDT 2024 \
Exercise `Running` added to Workout on 2024-03-14 \
Thu Apr 04 19:12:54 PDT 2024 \
New Workout created on 2024-03-14 \
Thu Apr 04 19:12:58 PDT 2024 \
Saved workouts to file


### Phase 4: Task 3
I think the design for this project is very simplified and therefore a good design. However,
there are still ways I could improve my design to allow for a more in depth breakdown of different
exercises. For example, to improve this project I can incorporate enumeration into my various
Exercise subtypes. This would allow for exercises of different types in one workout routine list
to be operated on in ways I cannot do with my current design. For example, running and bench press
are two different exercises, however the running exercise will have details of its time, duration, and
distance. Whereas the bench press exercise will have details of its sets and reps. But both of
these are subtypes of Exercise, which the user keeps a list of, therefore in my current design,
when calculating stats for entire workout routines, I have to deal with some awkward cases of exercises
with no distance. If I refactored this project to use enumeration, I could filter through the exercises.




*Citations*:

TellerApp - https://github.students.cs.ubc.ca/CPSC210/TellerApp \
JsonSerializationDemo - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo \
Java Swing Syntax - https://www.youtube.com/watch?v=5o3fMLPY7qY \
Java Swing Tutorial - https://www.javatpoint.com/java-swing 
