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

import java.net.URL;
import java.util.ResourceBundle;

public class ParkingMainViewController implements Initializable {

    @FXML
    private Button exitBtn, entBillBtn, modifyBtn, aboutBtn, costRateBtn;

    @FXML
    private Label userLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLbl.setText(BaseController.user.getUserName());

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        entBillBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EntranceBillView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Entrance & Bill");
                stage.show();
                entBillBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        modifyBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Modify");
                stage.show();
                modifyBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        costRateBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingCostRateView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Cost Rate");
                stage.show();
                costRateBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        aboutBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingAboutView.fxml")));
                stage.setScene(scene);
                stage.setTitle("About");
                stage.show();
                aboutBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

    }
}
