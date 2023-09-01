package project;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class ProjectApp extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("App.fxml"));
        stage.setScene(new Scene(parent));
        stage.show(); 
    }
    public static void main(String[] args) {
        launch(ProjectApp.class, args);
    }
    
}
