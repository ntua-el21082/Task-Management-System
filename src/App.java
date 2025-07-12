import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScene.fxml"));
            Scene scene = new Scene(root);
    
            primaryStage.setTitle("MediaLab Assistant");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Could not load MainScene.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
