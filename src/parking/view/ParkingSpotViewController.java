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
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import parking.controller.BaseController;
import parking.controller.FormType;
import parking.controller.ParkingSpotController;
import parking.model.entity.Car;
import parking.model.entity.ParkingSpot;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ParkingSpotViewController implements Initializable {

    @FXML
    private TextField spotIdTxt, spotNameTxt;

    @FXML
    private ComboBox<String> spotStatusCmb, spotVipCmb, delCmb;

    @FXML
    private Button spotSaveBtn, findAllBtn, spotVipGenFindBtn, statusFindBtn, nameFindBtn, idFindBtn, backBtn, exitBtn, clearBtn;

    @FXML
    private Label idLbl, userTitleLbl, userLbl, nameLbl, statusLbl, delLbl, vipLbl;

    @FXML
    private Shape lineShape;

    @FXML
    private Pane spotPane;

    @FXML
    private TableView<ParkingSpot> spotTbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        spotStatusCmb.getItems().add("Empty");
        spotStatusCmb.getItems().add("Filled");

        spotVipCmb.getItems().add("ON");
        spotVipCmb.getItems().add("OFF");

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
                BaseController.formType = FormType.ParkingSpot;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Parking Spot Modify");
                stage.show();
                backBtn.getScene().getWindow().hide();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        if (BaseController.formType.equals(FormType.Spot_save)) {
            spotPane.setPrefWidth(435);
            spotPane.setPrefHeight(575);
            backBtn.setLayoutX(65);
            backBtn.setLayoutY(504);
            exitBtn.setLayoutY(504);
            exitBtn.setLayoutX(286);
            userTitleLbl.setLayoutX(66);
            userTitleLbl.setLayoutY(384);
            userLbl.setLayoutX(155);
            userLbl.setLayoutY(384);
            spotTbl.setVisible(false);
            findAllBtn.setVisible(false);
            nameFindBtn.setVisible(false);
            idFindBtn.setVisible(false);
            spotVipGenFindBtn.setVisible(false);
            statusFindBtn.setVisible(false);
            lineShape.setVisible(false);
            delLbl.setVisible(false);
            delCmb.setVisible(false);
            idLbl.setVisible(false);
            spotIdTxt.setVisible(false);
            statusLbl.setVisible(false);
            spotStatusCmb.setVisible(false);

            spotSaveBtn.setOnAction(event -> {
                boolean vipStatus = spotVipCmb.getSelectionModel().getSelectedItem().equals("ON");
                Map<String, String> result = ParkingSpotController.save(spotNameTxt.getText(), vipStatus);
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });
        } else if (BaseController.formType.equals(FormType.Spot_edit)) {

            spotSaveBtn.setText("Edit");
            showAllDataOnTable();
            delCmb.setVisible(false);
            delLbl.setVisible(false);

            spotSaveBtn.setOnAction(event -> {
                boolean vipStatus = spotVipCmb.getSelectionModel().getSelectedItem().equals("ON");
                boolean status = spotStatusCmb.getSelectionModel().getSelectedItem().equals("Empty");
                Map<String, String> result = ParkingSpotController.edit(Long.parseLong(spotIdTxt.getText()), spotNameTxt.getText(), status, vipStatus);
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });

        } else if (BaseController.formType.equals(FormType.Spot_remove)) {

            spotSaveBtn.setText("Remove");
            showAllDataOnTable();

            nameFindBtn.setVisible(false);
            spotVipGenFindBtn.setVisible(false);
            statusFindBtn.setVisible(false);
            delLbl.setVisible(false);
            delCmb.setVisible(false);
            statusLbl.setVisible(false);
            spotStatusCmb.setVisible(false);
            vipLbl.setVisible(false);
            spotVipCmb.setVisible(false);
            nameLbl.setVisible(false);
            spotNameTxt.setVisible(false);

            spotSaveBtn.setOnAction(event -> {
                Map<String, String> result = ParkingSpotController.remove(Long.parseLong(spotIdTxt.getText()));
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });

        } else if (BaseController.formType.equals(FormType.Spot_find)) {

            spotSaveBtn.setVisible(false);
            delLbl.setVisible(false);
            delCmb.setVisible(false);
            findAllBtn.setLayoutX(65);
        }

        findAllBtn.setOnAction(event -> {
            showAllDataOnTable();
        });

        idFindBtn.setOnAction(event -> {
            Map<String, List<ParkingSpot>> result = ParkingSpotController.findById(Long.parseLong(spotIdTxt.getText()));
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        nameFindBtn.setOnAction(event -> {
            Map<String, List<ParkingSpot>> result = ParkingSpotController.findByName(spotNameTxt.getText());
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        statusFindBtn.setOnAction(event -> {
            boolean status = spotStatusCmb.getSelectionModel().getSelectedItem().equals("Empty");
            Map<String, List<ParkingSpot>> result = ParkingSpotController.findByStatus(status);
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        spotVipGenFindBtn.setOnAction(event -> {
            boolean vipStatus = spotVipCmb.getSelectionModel().getSelectedItem().equals("ON");
            Map<String, List<ParkingSpot>> result = ParkingSpotController.findByVipStatus(vipStatus);
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        spotTbl.setOnMouseClicked((event) -> {
            ParkingSpot parkingSpot = spotTbl.getSelectionModel().getSelectedItem();
            if (parkingSpot != null){
                selectTableRow(parkingSpot);
            }
        });

        spotTbl.setOnKeyReleased((event) -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
                ParkingSpot parkingSpot = spotTbl.getSelectionModel().getSelectedItem();
                if (parkingSpot != null) {
                    selectTableRow(parkingSpot);
                }
            }
        });

    }

    private void selectTableRow(ParkingSpot parkingSpot){
        spotIdTxt.setText(String.valueOf(parkingSpot.getId()));
        spotNameTxt.setText(parkingSpot.getName());
        if (parkingSpot.isStatus()){
            spotStatusCmb.setValue("Empty");
        } else {
            spotStatusCmb.setValue("Filled");
        }
        if (parkingSpot.isVipParking()){
            spotVipCmb.setValue("ON");
        }else {
            spotVipCmb.setValue("OFF");
        }
        if (parkingSpot.isDeleted()){
            delCmb.setValue("Yes");
        }else {
            delCmb.setValue("No");
        }
    }

    private void resetForm() {
        spotIdTxt.clear();
        spotNameTxt.clear();
        spotStatusCmb.getSelectionModel().clearSelection();
        spotVipCmb.getSelectionModel().clearSelection();
        delCmb.getSelectionModel().clearSelection();
        spotTbl.getColumns().clear();
    }

    private void showAllDataOnTable(){
        try {
            Map<String, List<ParkingSpot>> result = ParkingSpotController.findAll();
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }

    private void showDataOnTable(List<ParkingSpot> parkingSpotList) {
        spotTbl.getColumns().clear();

        try {
            ObservableList<ParkingSpot> parkingSpots = FXCollections.observableList(parkingSpotList);

            TableColumn<ParkingSpot, Long> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<ParkingSpot, String> nameCol = new TableColumn<>("NAME");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<ParkingSpot, Boolean> statusCol = new TableColumn<>("STATUS");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            TableColumn<ParkingSpot, Boolean> vipCol = new TableColumn<>("VIP");
            vipCol.setCellValueFactory(new PropertyValueFactory<>("vipParking"));

            TableColumn<ParkingSpot, Boolean> deletedCol = new TableColumn<>("DELETED");
            deletedCol.setCellValueFactory(new PropertyValueFactory<>("deleted"));

            spotTbl.getColumns().addAll(idCol, nameCol, statusCol, vipCol, deletedCol);
            spotTbl.setItems(parkingSpots);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }
}
