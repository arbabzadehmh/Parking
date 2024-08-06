package parking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parking.controller.UserController;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        UserController.save("admin", "admin","admin","admin",null);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/ParkingLoginView.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}
