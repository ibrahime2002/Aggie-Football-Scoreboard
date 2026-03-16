package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Scoreboard;

public class ScoreboardController {

    private final Scoreboard model = new Scoreboard();

    @FXML private TextField homeNameField;
    @FXML private TextField awayNameField;
    @FXML private Button setNamesButton;

    @FXML private RadioButton homeRadio;
    @FXML private RadioButton awayRadio;
    private ToggleGroup teamToggle;

    @FXML private Button btn6;
    @FXML private Button btn3;
    @FXML private Button btn2;
    @FXML private Button btn1;
    @FXML private Button undoBtn;
    @FXML private Button clearBtn;

    @FXML private Label homeNameDisplay;
    @FXML private Label awayNameDisplay;
    @FXML private Label homeScoreLabel;
    @FXML private Label awayScoreLabel;
    @FXML private Label lastActionLabel;

    @FXML
    public void initialize() {
        // setup toggle group
        teamToggle = new ToggleGroup();
        homeRadio.setToggleGroup(teamToggle);
        awayRadio.setToggleGroup(teamToggle);
        homeRadio.setSelected(true);

        // button actions
        setNamesButton.setOnAction(e -> handleSetNames());
        btn6.setOnAction(e -> handleAddPoints(6, "TD"));
        btn3.setOnAction(e -> handleAddPoints(3, "FG"));
        btn2.setOnAction(e -> handleAddPoints(2, "2PT"));
        btn1.setOnAction(e -> handleAddPoints(1, "XP"));

        undoBtn.setOnAction(e -> {
            try {
                model.undoLast();
                refreshView();
            } catch (IllegalStateException ex) {
                showAlert("Nothing to undo", ex.getMessage());
            }
        });

        clearBtn.setOnAction(e -> {
            model.clearGame();
            refreshView();
        });

        refreshView();
    }

    private void handleSetNames() {
        try {
            model.setTeamNames(homeNameField.getText(), awayNameField.getText());
            refreshView();
        } catch (IllegalArgumentException ex) {
            showAlert("Invalid team names", ex.getMessage());
        }
    }

    private void handleAddPoints(int pts, String label) {
        try {
            boolean homeSelected = homeRadio.isSelected();
            if (homeSelected) model.addPointsToHome(pts, label);
            else model.addPointsToAway(pts, label);
            refreshView();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            showAlert("Cannot add points", ex.getMessage());
        }
    }

    private void refreshView() {
        homeNameDisplay.setText(model.getHomeName().isEmpty() ? "Home" : model.getHomeName());
        awayNameDisplay.setText(model.getAwayName().isEmpty() ? "Away" : model.getAwayName());
        homeScoreLabel.setText(String.valueOf(model.getHomeScore()));
        awayScoreLabel.setText(String.valueOf(model.getAwayScore()));
        lastActionLabel.setText(model.getLastActionDescription().orElse("No actions yet"));
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
