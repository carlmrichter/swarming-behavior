package launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FishSimulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("FishSimulator Launcher");

        FXMLLoader fxmlLoader = new FXMLLoader();
        
        try {
            Parent root = fxmlLoader.load(this.getClass().getResource("layout/launcher.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
