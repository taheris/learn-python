package com.taheris.learnpython.gui;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import com.taheris.learnpython.App;
import com.taheris.learnpython.exercises.Mark;
import com.taheris.learnpython.helpers.StringMethods;

public class QuizDragController extends AbstractController {
    @FXML private AnchorPane apDraggable;
    @FXML private AnchorPane apExit;
    @FXML private AnchorPane apHome;
    @FXML private AnchorPane apMain;
    @FXML private AnchorPane apMessage;
    @FXML private AnchorPane apSidebar;
    @FXML private Button btnHint;
    @FXML private Button btnLearn;
    @FXML private Button btnQuiz;
    @FXML private Button btnReset;
    @FXML private Button btnSubmit;
    @FXML private FlowPane fpActions;
    @FXML private FlowPane fpSidebar;
    @FXML private HBox hbMain;
    @FXML private HBox hbNavbar;
    @FXML private HBox hbNavigate;
    @FXML private HBox hbTarget;
    @FXML private ImageView ivExit;
    @FXML private ImageView ivHome;
    @FXML private ImageView ivMark;
    @FXML private ImageView ivNext;
    @FXML private ImageView ivPrevious;
    @FXML private ImageView ivTarget;
    @FXML private Label lblAnswer;
    @FXML private Label lblAnswers;
    @FXML private Label lblMessage;
    @FXML private Label lblQuestion;
    @FXML private TextArea taQuestion;
    @FXML private VBox vbMain;
    @FXML private VBox vbRoot;

    private final ObservableList<String> answers = FXCollections.observableArrayList();
    private final ListChangeListener<String> listener = new ListChangeListener<String>() {
        @Override
        public void onChanged(Change<? extends String> change) {
            lblAnswers.setText(StringMethods.stripSquareBrackets(change.getList().toString()));
        }
    };
    private double deltaX;
    private double deltaY;
    private int colourIndex = 0;
    private final String[] colours = {
            "#f0f8ff", "#faebd7", "#00ffff", "#7fffd4", "#f0ffff", "#f5f5dc",
            "#ffe4c4", "#000000", "#ffebcd", "#0000ff", "#8a2be2", "#a52a2a",
            "#deb887", "#5f9ea0", "#7fff00", "#d2691e", "#ff7f50", "#6495ed",
            "#fff8dc", "#dc143c", "#00ffff", "#00008b", "#008b8b", "#b8860b"
    };

    /** bind answers list to a change listener */
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle relabels) {
        answers.addListener(listener);
    }

    /** show question text with options, and refresh mark status on startup */
    @Override
    public void windowShown() {
        taQuestion.setText(App.getGame().getExercise().getQuestion());

        if (App.getGame().getExercise().getQuestionCode() != null)
            taQuestion.setText(taQuestion.getText() + "\n\n" +
                    App.getGame().getExercise().joinQuestionCode());

        setMark(App.getGame().getExercise().getMark(), lblMessage, ivMark);
        drawOptions();
    }

    public void btnHintOnAction(ActionEvent event) {
        hintClick(lblMessage);
    }

    public void btnResetOnAction(ActionEvent event) {
        drawOptions();
    }

    /** set the mark status to correct or incorrect */
    public void btnSubmitOnAction(ActionEvent event) {
        if (App.getGame().getExercise().isValidAnswerOrAnswers(answers.toArray(new String[0])))
            setMark(Mark.CORRECT, lblMessage, ivMark);
        else
            setMark(Mark.INCORRECT, lblMessage, ivMark);
    }

    /** create a new label for each option text */
    private void drawOptions() {
        answers.clear();
        apDraggable.getChildren().clear();

        for (String text : App.getGame().getExercise().getOptions()) {
            newOption(text);
        }
    }

    /** create a draggable option label */
    private void newOption(String text) {
        final Label label = new Label(text);
        final DropShadow dropShadow = new DropShadow();
        final Glow glow = new Glow();
        Random random = new Random();
        String colour = colours[colourIndex++ % colours.length];

        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setEffect(dropShadow);
        label.setStyle("-fx-background-radius: 5; " +
                "-fx-background-color: linear-gradient(to bottom, " + colour + ", derive(" + colour + ", 20%)); " +
                "-fx-text-fill: ladder(" + colour +", lavender 49%, midnightblue 50%); " +
                "-fx-font-size: 16px; " +
                "-fx-padding: 5;");
        label.relocate(random.nextInt((int) (apDraggable.getWidth() - text.length() * 7)),
                random.nextInt((int) (apDraggable.getHeight() - 40)));

        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setCursor(Cursor.HAND);
                dropShadow.setInput(glow);
            }
        });

        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dropShadow.setInput(null);
            }
        });

        label.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deltaX = label.getLayoutX() - event.getSceneX();
                deltaY = label.getLayoutY() - event.getSceneY();
            }
        });

        label.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getSceneY() + deltaY > apDraggable.getHeight()) {
                    answers.add(label.getText());
                    label.setCursor(Cursor.DEFAULT);
                    label.setVisible(false);
                } else
                    label.setCursor(Cursor.HAND);
            }
        });

        label.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double newX = event.getSceneX() + deltaX;
                double newY = event.getSceneY() + deltaY;

                if (newX > 0 && newX < apDraggable.getWidth() - label.getWidth())
                    label.setLayoutX(newX);
                if (newY > 0 && newY < apDraggable.getHeight() + hbTarget.getHeight() - label.getHeight())
                    label.setLayoutY(newY);

                if (event.getSceneY() + deltaY > apDraggable.getHeight())
                    label.setCursor(Cursor.CROSSHAIR);
                else
                    label.setCursor(Cursor.CLOSED_HAND);
            }
        });

        apDraggable.getChildren().add(label);
    }
}
