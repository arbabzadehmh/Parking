package parking.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import parking.controller.BaseController;
import parking.controller.CarController;
import parking.controller.FormType;
import parking.model.entity.Car;

import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CarViewController implements Initializable {

    @FXML
    private TextField carIdTxt, carNumberTxt;

    @FXML
    private ComboBox<String> carStatusCmb, delCmb;

    @FXML
    private Button carSaveBtn, clearBtn, carFindAllBtn, backBtn, exitBtn, statusFindBtn, numberFindBtn, idFindBtn;

    @FXML
    private TableView<Car> carTbl;

    @FXML
    private Label userLbl, delLbl, numberLbl, statusLbl, idLbl;
    
    @FXML
    private Pane carPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        carStatusCmb.getItems().add("General");
        carStatusCmb.getItems().add("VIP");

        delCmb.getItems().add("Yes");
        delCmb.getItems().add("No");

        userLbl.setText(BaseController.user.getUserName());

        clearBtn.setOnAction(event -> {
            resetForm();
        });

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        backBtn.setOnAction(event -> {
            try {
                BaseController.formType = FormType.Car;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Car Modify");
                stage.show();
                backBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });


        if (BaseController.formType.equals(FormType.Car_save)){

            carPane.setPrefWidth(410);
            carPane.setPrefHeight(550);
            carTbl.setVisible(false);
            carFindAllBtn.setVisible(false);
            delCmb.setVisible(false);
            delLbl.setVisible(false);
            backBtn.setLayoutX(66);
            backBtn.setLayoutY(483);
            exitBtn.setLayoutX(258);
            exitBtn.setLayoutY(483);
            clearBtn.setLayoutX(258);
            carIdTxt.setVisible(false);
            idLbl.setVisible(false);
            numberFindBtn.setVisible(false);
            idFindBtn.setVisible(false);
            statusFindBtn.setVisible(false);

            carSaveBtn.setOnAction(event -> {
                boolean vipStatus = carStatusCmb.getSelectionModel().getSelectedItem().equals("VIP");
                Map<String, String> result = CarController.save(carNumberTxt.getText(), vipStatus);
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });
        } else if (BaseController.formType.equals(FormType.Car_edit)) {

            carSaveBtn.setText("Edit");
            showAllDataOnTable();
            delCmb.setVisible(false);
            delLbl.setVisible(false);

            carSaveBtn.setOnAction(event -> {
                boolean vipStatus = carStatusCmb.getSelectionModel().getSelectedItem().equals("VIP");
                Map<String, String> result = CarController.edit(Long.parseLong(carIdTxt.getText()), carNumberTxt.getText(), vipStatus);
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }

            });
        } else if (BaseController.formType.equals(FormType.Car_remove)) {
            
            carSaveBtn.setText("Remove");
            showAllDataOnTable();
            numberLbl.setVisible(false);
            carNumberTxt.setVisible(false);
            statusLbl.setVisible(false);
            carStatusCmb.setVisible(false);
            delLbl.setVisible(false);
            delCmb.setVisible(false);
            numberFindBtn.setVisible(false);
            statusFindBtn.setVisible(false);
            
            carSaveBtn.setOnAction(event -> {
                Map<String, String> result = CarController.remove(Long.parseLong(carIdTxt.getText()));
            if (result.get("status").equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                alert.show();
                showAllDataOnTable();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                alert.show();
            }
            });
        } else if (BaseController.formType.equals(FormType.Car_find)) {
            
            carSaveBtn.setVisible(false);
            carFindAllBtn.setLayoutX(66);
            delLbl.setVisible(false);
            delCmb.setVisible(false);

        } else if (BaseController.formType.equals(FormType.Car_select)) {
            delLbl.setVisible(false);
            delCmb.setVisible(false);
            carSaveBtn.setText("Select");

            backBtn.setOnAction(event -> {
                try {
                    BaseController.formType = FormType.Entrance_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EntranceFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Entrance Edit");
                    stage.show();
                    backBtn.getScene().getWindow().hide();
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                    alert.show();
                }
            });

            carSaveBtn.setOnAction(event -> {
                BaseController.carNumber = carNumberTxt.getText();
                try {
                    BaseController.formType = FormType.Entrance_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EntranceFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Entrance Edit");
                    stage.show();
                    carSaveBtn.getScene().getWindow().hide();
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                    alert.show();
                }
            });
        }

        carFindAllBtn.setOnAction(event -> {
            showAllDataOnTable();
        });

        idFindBtn.setOnAction(event -> {
            Map<String, List<Car>> result = CarController.findById(Long.parseLong(carIdTxt.getText()));
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        numberFindBtn.setOnAction(event -> {
            Map<String, List<Car>> result = CarController.findByNumber(carNumberTxt.getText());
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        statusFindBtn.setOnAction(event -> {
            boolean vipStatus = carStatusCmb.getSelectionModel().getSelectedItem().equals("VIP");
            Map<String, List<Car>> result = CarController.findByVipStatus(vipStatus);
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        carTbl.setOnMouseClicked((event) -> {
            Car car = carTbl.getSelectionModel().getSelectedItem();
            if (car != null){
                selectTableRow(car);
            }
        });

        carTbl.setOnKeyReleased((event) -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
                Car car = carTbl.getSelectionModel().getSelectedItem();
                if (car != null) {
                    selectTableRow(car);
                }
            }
        });

    }

    private void resetForm() {
        carIdTxt.clear();
        carNumberTxt.clear();
        carStatusCmb.getSelectionModel().clearSelection();
        delCmb.getSelectionModel().clearSelection();
        carTbl.getColumns().clear();
    }

    private void selectTableRow(Car car) {
        carIdTxt.setText(String.valueOf(car.getId()));
        carNumberTxt.setText(car.getCarNumber());
        if (car.isVip()){
            carStatusCmb.setValue("VIP");
        } else {
            carStatusCmb.setValue("General");
        }
        if (car.isDeleted()){
            delCmb.setValue("Yes");
        }else {
            delCmb.setValue("No");
        }
    }

    private void showAllDataOnTable(){
        try {
            Map<String, List<Car>> result = CarController.findAll();
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }

    private void showDataOnTable(List<Car> carList){
        carTbl.getColumns().clear();
        try {
                ObservableList<Car> cars = FXCollections.observableList(carList);

                TableColumn<Car, Long> idCol = new TableColumn<>("ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Car, String> numberCol = new TableColumn<>("CAR NUMBER");
                numberCol.setCellValueFactory(new PropertyValueFactory<>("carNumber"));

                TableColumn<Car, Boolean> vipCol = new TableColumn<>("VIP");
                vipCol.setCellValueFactory(new PropertyValueFactory<>("vip"));

                TableColumn<Car, Boolean> deletedCol = new TableColumn<>("DELETED");
                deletedCol.setCellValueFactory(new PropertyValueFactory<>("deleted"));

                carTbl.getColumns().addAll(idCol, numberCol, vipCol, deletedCol);
                carTbl.setItems(cars);

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }

}
