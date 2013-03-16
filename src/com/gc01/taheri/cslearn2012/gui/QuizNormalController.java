package com.gc01.taheri.cslearn2012.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.gc01.taheri.cslearn2012.App;
import com.gc01.taheri.cslearn2012.exercises.Mark;

public class QuizNormalController extends AbstractController {
    @FXML private AnchorPane apExit;
    @FXML private AnchorPane apHome;
    @FXML private AnchorPane apMain;
    @FXML private AnchorPane apMessage;
    @FXML private AnchorPane apSidebar;
    @FXML private Button btnHint;
    @FXML private Button btnLearn;
    @FXML private Button btnQuiz;
    @FXML private Button btnSubmit;
    @FXML private FlowPane fpActions;
    @FXML private FlowPane fpSidebar;
    @FXML private HBox hbMain;
    @FXML private HBox hbNavbar;
    @FXML private HBox hbNavigate;
    @FXML private ImageView ivExit;
    @FXML private ImageView ivHome;
    @FXML private ImageView ivMark;
    @FXML private ImageView ivNext;
    @FXML private ImageView ivPrevious;
    @FXML private Label lblAnswer;
    @FXML private Label lblMessage;
    @FXML private Label lblQuestion;
    @FXML private TextArea taAnswer;
    @FXML private TextArea taQuestion;
    @FXML private VBox vbMain;
    @FXML private VBox vbRoot;
    
    private boolean messageFlashing = false;
    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
    
    /** refresh question text and mark status on startup */
    @Override
    public void windowShown() {
        taQuestion.setText(App.getGame().getExercise().getQuestion());
        taAnswer.setText("");
        
        if (App.getGame().getExercise().getQuestionCode() != null)
            taQuestion.setText(taQuestion.getText() + "\n\n" +
                    App.getGame().getExercise().joinQuestionCode());
        
        setMark(App.getGame().getExercise().getMark(), lblMessage, ivMark);
    }
    
    public void btnHintOnAction(ActionEvent event) {
        hintClick(lblMessage);
    }
    
    /** choose a suitable marking method for the question, then set mark */
    public void btnSubmitOnAction(ActionEvent event) {
        if (App.getGame().getExercise().getAnswer() != null && validAnswer())
            setMark(Mark.CORRECT, lblMessage, ivMark);
        else if (App.getGame().getExercise().getAnswerCode() != null && validAnswerCode())
            setMark(Mark.CORRECT, lblMessage, ivMark);
        else if (App.getGame().getExercise().getOutput() != null && validOutput())
            setMark(Mark.CORRECT, lblMessage, ivMark);
        else if (App.getGame().getExercise().getCheck() != null && validChecks())
            setMark(Mark.CORRECT, lblMessage, ivMark);
        else if (!messageFlashing)
            setMark(Mark.INCORRECT, lblMessage, ivMark);
        
        messageFlashing = false;
    }
    
    /** check if the user's answer text is valid */
    private boolean validAnswer() {
        try {
            if (App.getGame().getExercise().isValidAnswer(taAnswer.getText()))
                return true;
        } catch (Exception e) {
            flashMessage(lblMessage, e.getMessage());
            messageFlashing = true;
        }
        
        return false;
    }
    
    /** check if the user's answer code is valid */
    private boolean validAnswerCode() {
        try {
            if (App.getGame().getExercise().isValidAnswerCode(taAnswer.getText()))
                return true;
        } catch (Exception e) {
            flashMessage(lblMessage, e.getMessage());
            messageFlashing = true;
        }
        
        return false;
    }
    
    /** check if the user's code output is valid */
    private boolean validOutput() {
        try {
            if (App.getGame().getExercise().isValidOutput(App.getPython().exec(taAnswer.getText())))
                return true;
        } catch (Exception e) {
            flashMessage(lblMessage, e.getMessage());
            messageFlashing = true;
        }
        
        return false;
    }
    
    /** loop through all code checks and make sure each passes */
    private boolean validChecks() {
        try {
            App.getPython().execBuffer(taAnswer.getText());
            
            for (String check : App.getGame().getExercise().getCheck())
                if (!App.getGame().getExercise().isValidCheck(App.getPython().exec("print " + check))) {
                    flashMessage(lblMessage, "Failed test: " + check);
                    messageFlashing = true;
                    return false;
                }
            
            return true;
        } catch (Exception e) {
            flashMessage(lblMessage, e.getMessage());
            messageFlashing = true;
            return false;
        }
    }
}
