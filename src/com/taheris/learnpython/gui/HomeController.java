package com.taheris.learnpython.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.taheris.learnpython.App;
import com.taheris.learnpython.exercises.Difficulty;
import com.taheris.learnpython.game.Game;

public class HomeController extends AbstractController {
    @FXML private Button btnEasy;
    @FXML private Button btnHard;
    @FXML private Button btnMedium;
    @FXML private HBox hbDifficulty;
    @FXML private HBox hbNavbar;
    @FXML private HBox hbProgress;
    @FXML private Label lblDifficulty;
    @FXML private Label lblHello;
    @FXML private Label lblName;
    @FXML private ProgressIndicator piEasy;
    @FXML private ProgressIndicator piMedium;
    @FXML private ProgressIndicator piHard;
    @FXML private TextField tfName;
    @FXML private VBox vbMain;
    @FXML private VBox vbRoot;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }

    /** refresh the exercise completion status on startup */
    @Override
    public void windowShown() {
        if (App.getGame() == null)
            setProgressVisible(false);
        else {
            piEasy.setProgress(App.getGame().getScore(Difficulty.EASY));
            piMedium.setProgress(App.getGame().getScore(Difficulty.MEDIUM));
            piHard.setProgress(App.getGame().getScore(Difficulty.HARD));
            setProgressVisible(true);
        }
    }

    public void btnEasyOnAction(ActionEvent event) throws Exception {
        startGame(Difficulty.EASY);
    }

    public void btnMediumOnAction(ActionEvent event) throws Exception {
        startGame(Difficulty.MEDIUM);
    }

    public void btnHardOnAction(ActionEvent event) throws Exception {
        startGame(Difficulty.HARD);
    }

    private void setProgressVisible(boolean visible) {
        piEasy.setVisible(visible);
        piMedium.setVisible(visible);
        piHard.setVisible(visible);
    }

    /** start a game if new player or not yet started, then show Learn scene */
    private void startGame(Difficulty difficulty) throws Exception {
        if (App.getGame() == null
                || !App.getGame().getName().equals(tfName.getText()))
            App.setGame(new Game(tfName.getText(), difficulty));
        else
            App.getGame().setDifficulty(difficulty);

        App.setScene("/com/taheris/learnpython/gui/Learn.fxml");
    }
}
