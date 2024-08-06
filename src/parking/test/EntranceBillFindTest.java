package parking.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EntranceBillFindTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/EntranceBillFindView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Entrance And Bill Search");
        primaryStage.show();
    }
}
