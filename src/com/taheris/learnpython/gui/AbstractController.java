package com.taheris.learnpython.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.taheris.learnpython.App;
import com.taheris.learnpython.exercises.Mark;
import com.taheris.learnpython.exercises.QuestionType;

public abstract class AbstractController implements Initializable, WindowEvents {
    private static final int MESSAGE_DELAY = 5;
    private Label label;
    private Double initX;
    private Double initY;
    private final Timeline message = new Timeline(new KeyFrame(Duration.seconds(MESSAGE_DELAY),
            new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            clearMessage();
        }
    }));

    /** go to the Home Scene */
    public void ivHomeOnMouseClicked(MouseEvent event) throws Exception {
        App.setScene("/com/taheris/learnpython/gui/Home.fxml");
    }

    /** save game then exit */
    public void ivExitOnMouseClicked(MouseEvent event) throws Exception {
        if (App.getGame() != null)
            App.getGame().endGame();

        Platform.exit();
    }

    /** get initial screen position for moving */
    public void lblMessageOnMousePressed(MouseEvent event) {
        initX = event.getScreenX() - App.getStage().getX();
        initY = event.getScreenY() - App.getStage().getY();
    }

    /** move screen to new position */
    public void lblMessageOnMouseDragged(MouseEvent event) {
        App.getStage().setX(event.getScreenX() - initX);
        App.getStage().setY(event.getScreenY() - initY);
    }

    /** go back one exercise */
    public void ivPreviousOnMouseClicked(MouseEvent event) throws Exception {
        App.getGame().previousExercise();
        showQuiz();
    }

    /** go forward one exercise */
    public void ivNextOnMouseClicked(MouseEvent event) throws Exception {
        App.getGame().nextExercise();
        showQuiz();
    }

    /** go to the Learn Scene */
    public void btnLearnOnAction(ActionEvent event) throws Exception {
        App.setScene("/com/taheris/learnpython/gui/Learn.fxml");
    }

    /** if drag question, go to the Drag Quiz Scene, else go to the Normal Quiz Scene */
    public void showQuiz() throws Exception {
        if (App.getGame().getExercise().getQuestionType() == QuestionType.DRAG)
            App.setScene("/com/taheris/learnpython/gui/QuizDrag.fxml");
        else
            App.setScene("/com/taheris/learnpython/gui/QuizNormal.fxml");
    }

    /** open a new webpage with the hint URL */
    public void hintClick(Label label) {
        if (App.getGame().getExercise().getHint() != null)
            showWebpage(App.getGame().getExercise().getHint().toExternalForm());
        else
            flashMessage(label, "Sorry. No hint available for this exercise :'(");
    }

    /** set the exercise mark */
    public void setMark(Mark mark, Label lblMessage, ImageView ivMark) {
        App.getGame().getExercise().setMark(mark);

        switch (mark) {
        case CORRECT:
            flashMessage(lblMessage, "Correct :)");
            ivMark.setImage(new Image("/com/taheris/learnpython/gui/img/correct.png"));
            break;
        case INCORRECT:
            flashMessage(lblMessage, "Sorry, that is not the right answer :'(");
            ivMark.setImage(new Image("/com/taheris/learnpython/gui/img/incorrect.png"));
            break;
        default:
            ivMark.setImage(null);
            break;
        }
    }

    /** flash a messsage to the given label for 5 seconds */
    public void flashMessage(Label label, String text) {
        this.label = label;
        this.label.setText(text);
        message.play();
    }

    /** clear the message from the label */
    public void clearMessage() {
        this.label.setText("");
    }

    /** start a new window with the webpage loaded */
    public void showWebpage(String url) {
        Stage stage = new Stage();
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();

        engine.load(url);
        stage.setScene(new Scene(new Group(browser)));
        stage.show();
    }
}
