package parking.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import parking.controller.BaseController;
import parking.controller.FormType;

import java.net.URL;
import java.util.ResourceBundle;

public class ParkingModifyViewController implements Initializable {

    @FXML
    private Button exitBtn, backBtn, carBtn, spotBtn, entranceBtn, billBtn, userBtn;

    @FXML
    private Label userLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLbl.setText(BaseController.user.getUserName());

        backBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingMainView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Main");
                stage.show();
                backBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        carBtn.setOnAction(event -> {
            BaseController.formType = FormType.Car;
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Car Modify");
                stage.show();
                carBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        spotBtn.setOnAction(event -> {
            BaseController.formType = FormType.ParkingSpot;
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Parking Spot Modify");
                stage.show();
                spotBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        entranceBtn.setOnAction(event -> {
            BaseController.formType = FormType.Entrance;
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Entrance Modify");
                stage.show();
                entranceBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        billBtn.setOnAction(event -> {
            BaseController.formType = FormType.Bill;
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Bill Modify");
                stage.show();
                billBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        userBtn.setOnAction(event -> {
            BaseController.formType = FormType.User;
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("User Modify");
                stage.show();
                userBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

    }
}
