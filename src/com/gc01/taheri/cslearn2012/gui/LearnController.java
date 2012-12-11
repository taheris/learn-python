package com.gc01.taheri.cslearn2012.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.gc01.taheri.cslearn2012.App;

public class LearnController extends AbstractController {
    @FXML private AnchorPane apExit;
    @FXML private AnchorPane apHome;
    @FXML private AnchorPane apMain;
    @FXML private AnchorPane apMessage;
    @FXML private AnchorPane apSidebar;
    @FXML private Button btnClearPlayground;
    @FXML private Button btnLearn;
    @FXML private Button btnQuiz;
    @FXML private Button btnRunCode;
    @FXML private FlowPane fpActions;
    @FXML private FlowPane fpSidebar;
    @FXML private HBox hbMain;
    @FXML private HBox hbNavbar;
    @FXML private ImageView ivExit;
    @FXML private ImageView ivHome;
    @FXML private Label lblAnswer;
    @FXML private Label lblMessage;
    @FXML private Label lblQuestion;
    @FXML private SplitPane spPlayground;
    @FXML private TextArea taAnswer;
    @FXML private TextArea taOutput;
    @FXML private TextArea taQuestion;
    @FXML private VBox vbMain;
    @FXML private VBox vbRoot;
    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
    
    /** refresh the exercise question text on startup */
    @Override
    public void windowShown() {
        taQuestion.setText(App.getGame().getMaterialText());
    }
    
    public void btnQuizOnAction(ActionEvent event) throws Exception {
        showQuiz();
    }
    
    public void btnClearPlaygroundOnAction(ActionEvent event) {
        taOutput.clear();
        taAnswer.clear();
    }
    
    /** try to run the user code, flashing any error messages */
    public void btnRunCodeOnAction(ActionEvent event) {
        taOutput.clear();
        
        try {
            taOutput.setText(App.getPython().exec(taAnswer.getText()));
        } catch (TimeoutException e) {
            flashMessage(lblMessage, "Code timeout.");
        } catch (Exception e) {
            flashMessage(lblMessage, e.getMessage());
        }
    }
}
