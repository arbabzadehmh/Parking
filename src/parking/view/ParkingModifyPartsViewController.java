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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import parking.controller.BaseController;
import parking.controller.FormType;

import java.net.URL;
import java.util.ResourceBundle;

public class ParkingModifyPartsViewController implements Initializable {

    @FXML
    private Label userLbl;

    @FXML
    private Button exitBtn, backBtn, saveBtn, editBtn, removeBtn, findBtn;

    @FXML
    private ImageView saveImg, editImg, removeImg, findImg;

    @FXML
    private Pane partsPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLbl.setText(BaseController.user.getUserName());

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        backBtn.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Modify");
                stage.show();
                backBtn.getScene().getWindow().hide();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        if(BaseController.formType.equals(FormType.Entrance) || BaseController.formType.equals(FormType.Bill)){
            saveBtn.setVisible(false);
            saveImg.setVisible(false);
            editBtn.setLayoutX(85);
            editImg.setLayoutX(95);
            findImg.setLayoutX(253);
            findBtn.setLayoutX(252);
            removeBtn.setLayoutX(417);
            removeImg.setLayoutX(423);
            backBtn.setLayoutX(411);
            exitBtn.setLayoutX(462);
            partsPane.setPrefWidth(590);
        }

        saveBtn.setOnAction(event -> {
            try {
                if (BaseController.formType.equals(FormType.Car)) {
                    BaseController.formType = FormType.Car_save;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CarView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Car Save");
                    stage.show();
                    saveBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.ParkingSpot)) {
                    BaseController.formType = FormType.Spot_save;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingSpotView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Parking Spot Save");
                    stage.show();
                    saveBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.User)) {
                    BaseController.formType = FormType.User_save;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingUserView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("User Save");
                    stage.show();
                    saveBtn.getScene().getWindow().hide();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                if (BaseController.formType.equals(FormType.Car)) {
                    BaseController.formType = FormType.Car_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CarView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Car Edit");
                    stage.show();
                    editBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.ParkingSpot)) {
                    BaseController.formType = FormType.Spot_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingSpotView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Parking Spot Edit");
                    stage.show();
                    editBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.User)) {
                    BaseController.formType = FormType.User_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingUserView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("User Edit");
                    stage.show();
                    editBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.Entrance)) {
                    BaseController.formType = FormType.Entrance_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EntranceFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Entrance Edit");
                    stage.show();
                    editBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.Bill)) {
                    BaseController.formType = FormType.Bill_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("BillFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Bill Edit");
                    stage.show();
                    editBtn.getScene().getWindow().hide();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        removeBtn.setOnAction(event -> {
            try {
                if (BaseController.formType.equals(FormType.Car)) {
                    BaseController.formType = FormType.Car_remove;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CarView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Car Remove");
                    stage.show();
                    removeBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.ParkingSpot)) {
                    BaseController.formType = FormType.Spot_remove;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingSpotView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Parking Spot Remove");
                    stage.show();
                    removeBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.User)) {
                    BaseController.formType = FormType.User_remove;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingUserView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("User Remove");
                    stage.show();
                    removeBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.Entrance)) {
                    BaseController.formType = FormType.Entrance_remove;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EntranceFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Entrance Remove");
                    stage.show();
                    removeBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.Bill)) {
                    BaseController.formType = FormType.Bill_remove;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("BillFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Bill Remove");
                    stage.show();
                    removeBtn.getScene().getWindow().hide();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        findBtn.setOnAction(event -> {
            try {
                if (BaseController.formType.equals(FormType.Car)) {
                    BaseController.formType = FormType.Car_find;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CarView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Car Find");
                    stage.show();
                    findBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.ParkingSpot)) {
                    BaseController.formType = FormType.Spot_find;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingSpotView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Parking Spot Find");
                    stage.show();
                    findBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.User)) {
                    BaseController.formType = FormType.User_find;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingUserView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("User Find");
                    stage.show();
                    findBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.Entrance)) {
                    BaseController.formType = FormType.Entrance_find;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EntranceFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Entrance Find");
                    stage.show();
                    findBtn.getScene().getWindow().hide();
                } else if (BaseController.formType.equals(FormType.Bill)) {
                    BaseController.formType = FormType.Bill_find;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("BillFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Bill Find");
                    stage.show();
                    findBtn.getScene().getWindow().hide();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

    }
}
