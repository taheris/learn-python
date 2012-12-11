package com.gc01.taheri.cslearn2012;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.gc01.taheri.cslearn2012.game.Game;
import com.gc01.taheri.cslearn2012.gui.Scenes;
import com.gc01.taheri.cslearn2012.python.Python;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> CS Learn 2012 Coursework
 * 
 *   <dt> Description:
 *   <dd> JavaFX app to teach the Python programming language
 * </dl>
 * 
 * @author Shaun Taheri
 * @version 29 Oct 2012
 */

public class App extends Application {
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;
    private static final App instance = new App();
    private static final Scenes scenes = new Scenes();
    private static final Python python = new Python();
    private static Stage stage;
    private static Game game;
    
    public static App getInstance() {
        return instance;
    }
    
    public static Scenes getScenes() {
        return App.scenes;
    }
    
    public static Python getPython() {
        return App.python;
    }
    
    public static Stage getStage() {
        return App.stage;
    }
    
    public static Game getGame() {
        return App.game;
    }
    
    public static void setGame(Game game) {
        App.game = game;
    }
    
    /** switch scene keeping the old one for future use */
    public static void setScene(String fxml) throws Exception {
        stage.setScene(scenes.getScene(fxml));
        scenes.getController(fxml).windowShown();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /** start the app defaulting to the Home view */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Learn Python");
        stage.initStyle(StageStyle.TRANSPARENT);
        setScene("/com/gc01/taheri/cslearn2012/gui/Home.fxml");
        stage.show();
    }
}
