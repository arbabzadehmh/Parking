package parking.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EntranceBillTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/EntranceBillView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Entrance and Bill");
        primaryStage.show();
    }
}
