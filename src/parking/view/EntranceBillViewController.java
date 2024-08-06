package parking.view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.stage.Stage;
import parking.controller.*;
import parking.model.entity.Car;
import parking.model.entity.ParkingBill;


import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class EntranceBillViewController implements Initializable {

    @FXML
    private TextField vipTxt, carNumberTxt, entIdTxt, costTxt, generalCapacityTxt, vipCapacityTxt, billCNTxt, billvipTxt, spotTxt, entTimeTxt;

    @FXML
    private ComboBox<String> spotCmb;

    @FXML
    private Button entranceSaveBtn, billSaveBtn, backBtn, exitBtn, clearBtn;

    @FXML
    private Label userLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startUp();

        generalCapacityTxt.setText(String.valueOf(ParkingSpotController.remainingCapacityOfGeneralParking()));
        vipCapacityTxt.setText(String.valueOf(ParkingSpotController.remainingCapacityOfVipParking()));

        userLbl.setText(BaseController.user.getUserName());

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        backBtn.setOnAction(event -> {
            try {
                BaseController.formType = FormType.Car;
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

        clearBtn.setOnAction(event -> {
            startUp();
            carNumberTxt.clear();
            vipTxt.clear();
            spotCmb.getSelectionModel().clearSelection();
            entIdTxt.clear();
            costTxt.clear();
            entTimeTxt.clear();
            billvipTxt.clear();
            billCNTxt.clear();
            spotTxt.clear();
        });

        entranceSaveBtn.setOnAction(event -> {
            Map<String, String> result = ParkingEntranceController.save(carNumberTxt.getText(), spotCmb.getSelectionModel().getSelectedItem());
            if (result.get("status").equals("true")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                alert.show();
                startUp();
                generalCapacityTxt.setText(String.valueOf(ParkingSpotController.remainingCapacityOfGeneralParking()));
                vipCapacityTxt.setText(String.valueOf(ParkingSpotController.remainingCapacityOfVipParking()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                alert.show();
            }
        });

        carNumberTxt.setOnKeyReleased(event -> {
            Car car = CarController.findByNumberTextField(carNumberTxt.getText());
            if (car != null) {
                if (car.isVip()) {
                    vipTxt.setText("Yes");
                } else {
                    vipTxt.setText("No");
                }
            } else {
                vipTxt.setText("No");
            }
        });

        billSaveBtn.setOnAction(event -> {
            Map<String, String> result = ParkingBillController.save(Long.parseLong(entIdTxt.getText()));
            if (result.get("status").equals("true")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                alert.show();

                startUp();

                generalCapacityTxt.setText(String.valueOf(ParkingSpotController.remainingCapacityOfGeneralParking()));
                vipCapacityTxt.setText(String.valueOf(ParkingSpotController.remainingCapacityOfVipParking()));

                ParkingBill parkingBill = ParkingBillController.findByEntranceIdTextField(Long.parseLong(entIdTxt.getText()));
                billCNTxt.setText(parkingBill.getParkingEntrance().getCar().getCarNumber());
                if (parkingBill.getParkingEntrance().getCar().isVip()) {
                    billvipTxt.setText("Yes");
                } else {
                    billvipTxt.setText("No");
                }
                spotTxt.setText(parkingBill.getParkingEntrance().getParkingSpot().getName());
                entTimeTxt.setText(String.valueOf(parkingBill.getParkingEntrance().getEntranceTime()));
                costTxt.setText(String.valueOf(parkingBill.getCost()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                alert.show();
            }
        });
    }

    private void startUp() {

        spotCmb.getItems().clear();

        List<String> emptySpotNames = ParkingSpotController.findEmptySpot();
        for (String spot : emptySpotNames) {
            spotCmb.getItems().add(spot);
        }

        carNumberTxt.clear();
        vipTxt.clear();
        spotCmb.getSelectionModel().clearSelection();
    }
}
