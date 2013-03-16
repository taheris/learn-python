package com.gc01.taheri.cslearn2012.gui;

import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.paint.Color;
import com.gc01.taheri.cslearn2012.App;

public class Scenes {
    private final HashMap<String, Scene> scenes = new HashMap<String, Scene>();
    private final HashMap<String, AbstractController> controllers =
            new HashMap<String, AbstractController>();
    
    /** create scene if using for the first time, else return previously used scene */
    public Scene getScene(String fxml) throws Exception {
        if (!scenes.containsKey(fxml))
            scenes.put(fxml, createScene(fxml));
        
        return scenes.get(fxml);
    }
    
    public AbstractController getController(String fxml) {
        return controllers.get(fxml);
    }
    
    /** create a new scene with the given FXML file */
    private Scene createScene(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent parent = (Parent) loader.load();
        Scene scene = SceneBuilder.create()
                .fill(Color.TRANSPARENT)
                .width(App.APP_WIDTH)
                .height(App.APP_HEIGHT)
                .root(parent)
                .build();
        
        controllers.put(fxml, (AbstractController) loader.getController());
        return scene;
    }
}
