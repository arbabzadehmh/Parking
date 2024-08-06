package parking.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import parking.controller.BaseController;
import parking.controller.CarController;
import parking.controller.CostRateController;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ParkingCostRateViewController implements Initializable {

    @FXML
    private Button backBtn, exitBtn, saveBtn;

    @FXML
    private TextField entranceFeeTxt, holidaysRateTxt, dRateTxt, hRateTxt, idTxt;

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
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        if (BaseController.costRate == null){

            saveBtn.setOnAction(event -> {
                Map<String, String> result = CostRateController.save(Double.parseDouble(hRateTxt.getText()), Double.parseDouble(dRateTxt.getText()), Double.parseDouble(holidaysRateTxt.getText()), Double.parseDouble(entranceFeeTxt.getText()));
                if (result.get("status").equals("true")){
                    BaseController.costRate = CostRateController.find();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showDataOnTextField();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });

        } else {
            saveBtn.setText("Edit");
            showDataOnTextField();

            saveBtn.setOnAction(event -> {
                Map<String, String> result = CostRateController.edit(Double.parseDouble(hRateTxt.getText()), Double.parseDouble(dRateTxt.getText()), Double.parseDouble(holidaysRateTxt.getText()), Double.parseDouble(entranceFeeTxt.getText()));
                if (result.get("status").equals("true")){
                    BaseController.costRate = CostRateController.find();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });

        }

    }

    private void showDataOnTextField(){
        idTxt.setText(String.valueOf(BaseController.costRate.getId()));
        hRateTxt.setText(String.valueOf(BaseController.costRate.getHourlyRate()));
        dRateTxt.setText(String.valueOf(BaseController.costRate.getDailyRate()));
        holidaysRateTxt.setText(String.valueOf(BaseController.costRate.getHolidaysHourlyRate()));
        entranceFeeTxt.setText(String.valueOf(BaseController.costRate.getEntranceFee()));
    }
}
