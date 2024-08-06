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
import javafx.stage.Stage;
import parking.controller.BaseController;
import parking.controller.FormType;
import parking.controller.ParkingBillController;


import parking.controller.ParkingEntranceController;
import parking.model.entity.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BillFindViewController implements Initializable {

    @FXML
    private TextField idFindTxt, numberFindTxt, entIdTxt;

    @FXML
    private Label numberLbl, entIdLbl, timeLbl, delLbl, userLbl;

    @FXML
    private TableView<ParkingBill> billEditTbl;

    @FXML
    private TableView<ParkingBillVo> billTbl;

    @FXML
    private DatePicker exitTimeFindDate;

    @FXML
    private ComboBox<Integer> hourCmb, minCmb;

    @FXML
    private ComboBox<String> delCmb;

    @FXML
    private Button editBtn, clearBtn, backBtn, exitBtn, findAllBtn, billIdFindBtn, numberFindBtn, entIdFindBtn, exitTimeFindBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLbl.setText(BaseController.user.getUserName());

        for (int i = 0; i <= 23; i++) {
            hourCmb.getItems().add(i);
        }

        for (int i = 0; i <= 59; i++) {
            minCmb.getItems().add(i);
        }

        delCmb.getItems().add("Yes");
        delCmb.getItems().add("No");

        clearBtn.setOnAction(event -> {
            resetForm();
        });

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        backBtn.setOnAction(event -> {
            try {
                BaseController.formType = FormType.Bill;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Bill Modify");
                stage.show();
                backBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        billEditTbl.setOnMouseClicked((event) -> {
            ParkingBill parkingBill = billEditTbl.getSelectionModel().getSelectedItem();
            if (parkingBill != null){
                selectTableRow(parkingBill);
            }
        });

        billEditTbl.setOnKeyReleased((event) -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
                ParkingBill parkingBill = billEditTbl.getSelectionModel().getSelectedItem();
                if (parkingBill != null) {
                    selectTableRow(parkingBill);
                }
            }
        });

        if (BaseController.formType.equals(FormType.Bill_edit)) {
            billTbl.setVisible(false);
            numberFindBtn.setVisible(false);
            numberFindTxt.setVisible(false);
            numberLbl.setVisible(false);
            delLbl.setVisible(false);
            delCmb.setVisible(false);
            entIdTxt.setText(String.valueOf(BaseController.parkingEntranceId));
            entIdFindBtn.setText("Select");

            entIdFindBtn.setOnAction(event -> {
                try {
                    BaseController.formType = FormType.Entrance_select;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EntranceFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Entrance Select");
                    stage.show();
                    entIdFindBtn.getScene().getWindow().hide();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                    alert.show();
                }
            });


            editBtn.setOnAction(event -> {
                LocalDateTime exitTime = exitTimeFindDate.getValue().atTime(hourCmb.getSelectionModel().getSelectedItem(), minCmb.getSelectionModel().getSelectedItem());
                Map<String, String> result = ParkingBillController.edit(Long.parseLong(idFindTxt.getText()), Long.parseLong(entIdTxt.getText()), exitTime);
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });

            findAllBtn.setOnAction(event -> {
                showAllDataOnTable();
            });

            billIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingBill>> result = ParkingBillController.findById(Long.parseLong(idFindTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTable(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            exitTimeFindBtn.setOnAction(event -> {
                LocalDateTime exitTime = exitTimeFindDate.getValue().atTime(hourCmb.getSelectionModel().getSelectedItem(), minCmb.getSelectionModel().getSelectedItem());
                Map<String, List<ParkingBill>> result = ParkingBillController.findByExitTime(exitTime);
                if (result.containsKey("ok")){
                    showDataOnTable(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

        } else if (BaseController.formType.equals(FormType.Bill_remove)) {
            billTbl.setVisible(false);
            editBtn.setText("Remove");
            numberLbl.setVisible(false);
            numberFindTxt.setVisible(false);
            numberFindBtn.setVisible(false);
            entIdTxt.setVisible(false);
            entIdLbl.setVisible(false);
            entIdFindBtn.setVisible(false);
            exitTimeFindDate.setVisible(false);
            exitTimeFindBtn.setVisible(false);
            timeLbl.setVisible(false);
            hourCmb.setVisible(false);
            minCmb.setVisible(false);
            delCmb.setVisible(false);
            delLbl.setVisible(false);

            billIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingBill>> result = ParkingBillController.findById(Long.parseLong(idFindTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTable(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            findAllBtn.setOnAction(event -> {
                showAllDataOnTable();
            });

            editBtn.setOnAction(event -> {
                Map<String, String> result = ParkingBillController.remove(Long.parseLong(idFindTxt.getText()));
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });


        } else if (BaseController.formType.equals(FormType.Bill_find)) {

            billEditTbl.setVisible(false);
            editBtn.setVisible(false);
            delCmb.setVisible(false);
            delLbl.setVisible(false);
            findAllBtn.setLayoutX(69);
            findAllBtn.setPrefWidth(101);

            findAllBtn.setOnAction(event -> {
                showAllDataOnTableFind();
            });

            billIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingBill>> result = ParkingBillController.findById(Long.parseLong(idFindTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            numberFindBtn.setOnAction(event -> {
                Map<String, List<ParkingBill>> result = ParkingBillController.findByCarNumber(numberFindTxt.getText());
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            entIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingBill>> result = ParkingBillController.findByEntranceId(Long.parseLong(entIdTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            exitTimeFindBtn.setOnAction(event -> {
                LocalDateTime exitTime = exitTimeFindDate.getValue().atTime(hourCmb.getSelectionModel().getSelectedItem(), minCmb.getSelectionModel().getSelectedItem());
                Map<String, List<ParkingBill>> result = ParkingBillController.findByExitTime(exitTime);
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

        }

    }

    private void resetForm() {
        idFindTxt.clear();
        numberFindTxt.clear();
        entIdTxt.clear();
        exitTimeFindDate.setValue(null);
        hourCmb.getSelectionModel().clearSelection();
        minCmb.getSelectionModel().clearSelection();
        delCmb.getSelectionModel().clearSelection();
        billEditTbl.getColumns().clear();
        billTbl.getColumns().clear();
    }

    private void selectTableRow(ParkingBill parkingBill) {
        idFindTxt.setText(String.valueOf(parkingBill.getId()));
        numberFindTxt.setText(parkingBill.getParkingEntrance().getCar().getCarNumber());
        entIdTxt.setText(String.valueOf(parkingBill.getParkingEntrance().getId()));
        exitTimeFindDate.setValue(LocalDate.from(parkingBill.getExitTime()));
        hourCmb.setValue(parkingBill.getExitTime().getHour());
        minCmb.setValue(parkingBill.getExitTime().getMinute());
        if (parkingBill.isDeleted()){
            delCmb.setValue("Yes");
        } else {
            delCmb.setValue("No");
        }
    }

    private void showAllDataOnTableFind(){
        try {
            Map<String, List<ParkingBill>> result = ParkingBillController.findAll();
            if (result.containsKey("ok")){
                showDataOnTableFind(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }

    private void showDataOnTableFind(List<ParkingBill> parkingBillList){

        List<ParkingBillVo> parkingBillVosVoList = new ArrayList<>();
        for (ParkingBill parkingBill : parkingBillList) {
            ParkingBillVo parkingBillVo = new ParkingBillVo(parkingBill);
            parkingBillVosVoList.add(parkingBillVo);
        }

        billTbl.getColumns().clear();

        try {
            ObservableList<ParkingBillVo> parkingBillVos = FXCollections.observableList(parkingBillVosVoList);

            TableColumn<ParkingBillVo, Long> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<ParkingBillVo, LocalDateTime> exitTimeCol = new TableColumn<>("EXIT TIME");
            exitTimeCol.setCellValueFactory(new PropertyValueFactory<>("exitTime"));

            TableColumn<ParkingBillVo, LocalDateTime> entTimeCol = new TableColumn<>("ENTRANCE TIME");
            entTimeCol.setCellValueFactory(new PropertyValueFactory<>("entTime"));

            TableColumn<ParkingBillVo, String> numberCol = new TableColumn<>("CAR NUMBER");
            numberCol.setCellValueFactory(new PropertyValueFactory<>("carNumber"));

            TableColumn<ParkingBillVo, Boolean> carVipCol = new TableColumn<>("CAR VIP");
            carVipCol.setCellValueFactory(new PropertyValueFactory<>("vipCar"));

            TableColumn<ParkingBillVo, String> spotNameCol = new TableColumn<>("PARKING SPOT");
            spotNameCol.setCellValueFactory(new PropertyValueFactory<>("spotName"));

            TableColumn<ParkingBillVo, Double> costCol = new TableColumn<>("COST");
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));


            billTbl.getColumns().addAll(idCol, numberCol, carVipCol, spotNameCol, entTimeCol, exitTimeCol, costCol);
            billTbl.setItems(parkingBillVos);

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }

    private void showAllDataOnTable(){
        try {
            Map<String, List<ParkingBill>> result = ParkingBillController.findAll();
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

    private void showDataOnTable(List<ParkingBill> parkingBillList){

        billEditTbl.getColumns().clear();

        try {
            ObservableList<ParkingBill> parkingBills = FXCollections.observableList(parkingBillList);

            TableColumn<ParkingBill, Long> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<ParkingBill, ParkingEntrance> entCol = new TableColumn<>("ENTRANCE");
            entCol.setCellValueFactory(new PropertyValueFactory<>("parkingEntrance"));

            TableColumn<ParkingBill, LocalDateTime> exitTimeCol = new TableColumn<>("EXIT TIME");
            exitTimeCol.setCellValueFactory(new PropertyValueFactory<>("exitTime"));

            TableColumn<ParkingBill, Double> costCol = new TableColumn<>("COST");
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

            TableColumn<ParkingBill, Boolean> delCol = new TableColumn<>("DELETED");
            delCol.setCellValueFactory(new PropertyValueFactory<>("deleted"));

            billEditTbl.getColumns().addAll(idCol, entCol, exitTimeCol, costCol, delCol);
            billEditTbl.setItems(parkingBills);

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }

}
