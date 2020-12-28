package api.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("windows/ProgramSelectWindow.fxml"));
        Scene  scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("AppGUIStyleSheet.css").toExternalForm());
        primaryStage.setTitle("Custom Language Interpreter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
