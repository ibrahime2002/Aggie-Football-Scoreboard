# CSCE 314 Final Project - MVC Scoreboard 🏈

**Author:** Ibrahime Muhammad

<img width="1071" height="685" alt="image" src="https://github.com/user-attachments/assets/8da12b4e-0282-45cf-ae78-d1b2f61f2344" />


## Description

This is a custom JavaFX application that functions as an American football scoreboard. It is built strictly using the **Model-View-Controller (MVC)** architectural pattern to ensure a clean separation of concerns. Users can set team names, add points (Touchdowns, Field Goals, Safeties, Extra Points), track the last action, undo errant entries, and clear the game state.

*(Optional: You can add a screenshot of your app here later by dragging an image into the GitHub editor!)*

## Features

* **Team Management:** Set home and away team names.
* **Live Scoring:** Add points to either team (+6 TD, +3 FG, +2 Safety, +1 XP).
* **Action Tracking:** Displays the most recent scoring action.
* **Undo Functionality:** Revert the last scoring action via a history stack.
* **Clear Game:** Reset scores to 0 while maintaining team names.
* **Input Validation:** Prevents scoring before teams are set and handles blank inputs gracefully with JavaFX Alerts.

## Architecture (MVC)

This project strictly enforces separation of concerns:

* **Model (`Scoreboard.java`, `ScoreChange.java`):** Handles pure business logic, scoring math, and the undo history queue. It contains **zero** JavaFX dependencies.
* **View (`scoreboard.fxml`, `styles.css`):** Defines the GUI layout and styling, built entirely in SceneBuilder.
* **Controller (`ScoreboardController.java`):** Acts as the bridge, capturing UI click events, calling Model methods, and updating the View text.

## File Structure

* `App.java` - Main JavaFX launcher.
* `ScoreboardController.java` - Handles user interactions.
* `Scoreboard.java` - Core business logic and state.
* `ScoreChange.java` - Stores individual score changes for the Undo feature.
* `ScoreboardTests.java` - Standalone test suite for the Model.
* `scoreboard.fxml` - JavaFX layout.
* `styles.css` - Custom styling and layout rules.

## How to Build and Run (CLI)

**Prerequisites:** JDK 17+ and JavaFX SDK 25+.

**1. Set up your JavaFX Path**
Note: In the commands below, replace `PATH_TO_FX` with the actual path to your downloaded JavaFX `lib` folder (e.g., `C:\Users\ibrah\Downloads\javafx-sdk-25.0.1\lib`).

**2. Compile the Project**

```powershell
javac --module-path "PATH_TO_FX" --add-modules javafx.controls,javafx.fxml -d bin src/app/*.java src/controller/*.java src/model/*.java
