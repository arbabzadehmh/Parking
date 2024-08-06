package parking.view;

import com.google.gson.Gson;
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
import parking.controller.*;


import parking.model.entity.Car;
import parking.model.entity.ParkingEntrance;
import parking.model.entity.ParkingEntranceVo;
import parking.model.entity.ParkingSpot;


import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EntranceFindViewController implements Initializable {

    @FXML
    private TextField entIdFindTxt, entCNumberFindTxt;

    @FXML
    private TableView<ParkingEntranceVo> entTbl;

    @FXML
    private TableView<ParkingEntrance> entEditTbl;

    @FXML
    private ComboBox<Integer> hourCmb, minCmb;

    @FXML
    private ComboBox<String> spotFindCmb, delCmb;

    @FXML
    private Label userLbl, delLbl, timeLbl, spotLbl, numberLbl;

    @FXML
    private DatePicker entTimeFindDate;

    @FXML
    private Button entIdFindBtn, entCNumberFindBtn, spotFindBtn, entTimeFindBtn, entFindAllBtn, clearBtn, backBtn, exitBtn, editBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLbl.setText(BaseController.user.getUserName());

        for (int i = 0; i <= 23; i++) {
            hourCmb.getItems().add(i);
        }

        for (int i = 0; i <= 59; i++) {
            minCmb.getItems().add(i);
        }

        Gson gson = new Gson();
        List<String> allSpotNames = gson.fromJson(ParkingSpotController.findAllNames(), ArrayList.class);
        for (String spot : allSpotNames){
            spotFindCmb.getItems().add(spot);
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
                BaseController.formType = FormType.Entrance;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Entrance Modify");
                stage.show();
                backBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        entEditTbl.setOnMouseClicked((event) -> {
            ParkingEntrance parkingEntrance = entEditTbl.getSelectionModel().getSelectedItem();
            if (parkingEntrance != null){
                selectTableRow(parkingEntrance);
            }
        });

        entEditTbl.setOnKeyReleased((event) -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
                ParkingEntrance parkingEntrance = entEditTbl.getSelectionModel().getSelectedItem();
                if (parkingEntrance != null) {
                    selectTableRow(parkingEntrance);
                }
            }
        });

        if (BaseController.formType.equals(FormType.Entrance_edit)){
            entTbl.setVisible(false);
            entCNumberFindTxt.setText(BaseController.carNumber);
            entCNumberFindBtn.setText("Select");
            delLbl.setVisible(false);
            delCmb.setVisible(false);

            entCNumberFindBtn.setOnAction(event -> {
                try {
                    BaseController.formType = FormType.Car_select;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CarView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Car Select");
                    stage.show();
                    entCNumberFindBtn.getScene().getWindow().hide();
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                    alert.show();
                }
            });

            editBtn.setOnAction(event -> {
                LocalDateTime entTime = entTimeFindDate.getValue().atTime(hourCmb.getSelectionModel().getSelectedItem(), minCmb.getSelectionModel().getSelectedItem());
                Map<String, String> result = ParkingEntranceController.edit(Long.parseLong(entIdFindTxt.getText()), entCNumberFindTxt.getText(), entTime, spotFindCmb.getSelectionModel().getSelectedItem());
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });

            entFindAllBtn.setOnAction(event -> {
                showAllDataOnTable();
            });

            entIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findById(Long.parseLong(entIdFindTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTable(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            spotFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findBySpotName(spotFindCmb.getSelectionModel().getSelectedItem());
                if (result.containsKey("ok")){
                    showDataOnTable(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            entTimeFindBtn.setOnAction(event -> {
                LocalDateTime entTime = entTimeFindDate.getValue().atTime(hourCmb.getSelectionModel().getSelectedItem(), minCmb.getSelectionModel().getSelectedItem());
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findByEntranceTime(entTime);
                if (result.containsKey("ok")){
                    showDataOnTable(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

        } else if (BaseController.formType.equals(FormType.Entrance_remove)) {
            entTbl.setVisible(false);
            editBtn.setText("Remove");
            delCmb.setVisible(false);
            delLbl.setVisible(false);
            entCNumberFindBtn.setVisible(false);
            entCNumberFindTxt.setVisible(false);
            numberLbl.setVisible(false);
            spotFindCmb.setVisible(false);
            spotLbl.setVisible(false);
            spotFindBtn.setVisible(false);
            timeLbl.setVisible(false);
            entTimeFindBtn.setVisible(false);
            entTimeFindDate.setVisible(false);
            hourCmb.setVisible(false);
            minCmb.setVisible(false);

            entIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findById(Long.parseLong(entIdFindTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTable(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            entFindAllBtn.setOnAction(event -> {
                showAllDataOnTable();
            });

            editBtn.setOnAction(event -> {
                Map<String, String> result = ParkingEntranceController.remove(Long.parseLong(entIdFindTxt.getText()));
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });


        } else if (BaseController.formType.equals(FormType.Entrance_find)) {
            entEditTbl.setVisible(false);
            editBtn.setVisible(false);
            delCmb.setVisible(false);
            delLbl.setVisible(false);
            entFindAllBtn.setLayoutX(69);
            entFindAllBtn.setPrefWidth(101);

            entFindAllBtn.setOnAction(event -> {
                showAllDataOnTableFind();
            });

            entIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findById(Long.parseLong(entIdFindTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            entCNumberFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findByCarNumber(entCNumberFindTxt.getText());
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            spotFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findBySpotName(spotFindCmb.getSelectionModel().getSelectedItem());
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            entTimeFindBtn.setOnAction(event -> {
                LocalDateTime entTime = entTimeFindDate.getValue().atTime(hourCmb.getSelectionModel().getSelectedItem(), minCmb.getSelectionModel().getSelectedItem());
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findByEntranceTime(entTime);
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

        } else if (BaseController.formType.equals(FormType.Entrance_select)) {

            entEditTbl.setVisible(false);
            editBtn.setText("Select");

            editBtn.setOnAction(event -> {
                BaseController.parkingEntranceId = Long.parseLong(entIdFindTxt.getText());
                try {
                    BaseController.formType = FormType.Bill_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("BillFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Bill Edit");
                    stage.show();
                    editBtn.getScene().getWindow().hide();
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                    alert.show();
                }
            });

            backBtn.setOnAction(event -> {
                try {
                    BaseController.formType = FormType.Bill_edit;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("BillFindView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Bill Edit");
                    stage.show();
                    backBtn.getScene().getWindow().hide();
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                    alert.show();
                }
            });

            entFindAllBtn.setOnAction(event -> {
                showAllDataOnTableFind();
            });

            entIdFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findById(Long.parseLong(entIdFindTxt.getText()));
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            entCNumberFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findByCarNumber(entCNumberFindTxt.getText());
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            spotFindBtn.setOnAction(event -> {
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findBySpotName(spotFindCmb.getSelectionModel().getSelectedItem());
                if (result.containsKey("ok")){
                    showDataOnTableFind(result.get("ok"));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                    alert.show();
                    resetForm();
                }
            });

            entTimeFindBtn.setOnAction(event -> {
                LocalDateTime entTime = entTimeFindDate.getValue().atTime(hourCmb.getSelectionModel().getSelectedItem(), minCmb.getSelectionModel().getSelectedItem());
                Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findByEntranceTime(entTime);
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
        entIdFindTxt.clear();
        entCNumberFindTxt.clear();
        spotFindCmb.getSelectionModel().clearSelection();
        entTimeFindDate.setValue(null);
        hourCmb.getSelectionModel().clearSelection();
        minCmb.getSelectionModel().clearSelection();
        delCmb.getSelectionModel().clearSelection();
        entTbl.getColumns().clear();
        entEditTbl.getColumns().clear();
    }

    private void selectTableRow(ParkingEntrance parkingEntrance) {
        entIdFindTxt.setText(String.valueOf(parkingEntrance.getId()));
        entCNumberFindTxt.setText(parkingEntrance.getCar().getCarNumber());
        spotFindCmb.setValue(parkingEntrance.getParkingSpot().getName());
        entTimeFindDate.setValue(LocalDate.from(parkingEntrance.getEntranceTime()));
        hourCmb.setValue(parkingEntrance.getEntranceTime().getHour());
        minCmb.setValue(parkingEntrance.getEntranceTime().getMinute());
        if (parkingEntrance.isDeleted()){
            delCmb.setValue("Yes");
        } else {
            delCmb.setValue("No");
        }
    }

    private void showAllDataOnTableFind(){
        try {
            Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findAll();
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

    private void showDataOnTableFind(List<ParkingEntrance> parkingEntranceList){

        List<ParkingEntranceVo> parkingEntranceVoList = new ArrayList<>();
        for (ParkingEntrance parkingEntrance : parkingEntranceList) {
            ParkingEntranceVo parkingEntranceVo = new ParkingEntranceVo(parkingEntrance);
            parkingEntranceVoList.add(parkingEntranceVo);
        }

        entTbl.getColumns().clear();

        try {
            ObservableList<ParkingEntranceVo> parkingEntranceVos = FXCollections.observableList(parkingEntranceVoList);

            TableColumn<ParkingEntranceVo, Long> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<ParkingEntranceVo, LocalDateTime> entTimeCol = new TableColumn<>("ENTRANCE TIME");
            entTimeCol.setCellValueFactory(new PropertyValueFactory<>("entranceTime"));

            TableColumn<ParkingEntranceVo, String> numberCol = new TableColumn<>("CAR NUMBER");
            numberCol.setCellValueFactory(new PropertyValueFactory<>("carNumber"));

            TableColumn<ParkingEntranceVo, Boolean> carVipCol = new TableColumn<>("CAR VIP");
            carVipCol.setCellValueFactory(new PropertyValueFactory<>("carVIP"));

            TableColumn<ParkingEntranceVo, String> spotNameCol = new TableColumn<>("PARKING SPOT");
            spotNameCol.setCellValueFactory(new PropertyValueFactory<>("spotName"));

            TableColumn<ParkingEntranceVo, Boolean> spotVipCol = new TableColumn<>("SPOT VIP");
            spotVipCol.setCellValueFactory(new PropertyValueFactory<>("spotVIP"));

            TableColumn<ParkingEntranceVo, Boolean> spotStatusCol = new TableColumn<>("SPOT STATUS");
            spotStatusCol.setCellValueFactory(new PropertyValueFactory<>("spotStatus"));

            entTbl.getColumns().addAll(idCol,entTimeCol, numberCol, carVipCol, spotNameCol, spotVipCol, spotStatusCol);
            entTbl.setItems(parkingEntranceVos);

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }

    private void showAllDataOnTable(){
        try {
            Map<String, List<ParkingEntrance>> result = ParkingEntranceController.findAll();
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

    private void showDataOnTable(List<ParkingEntrance> parkingEntranceList){

        entEditTbl.getColumns().clear();

        try {
            ObservableList<ParkingEntrance> parkingEntrances = FXCollections.observableList(parkingEntranceList);

            TableColumn<ParkingEntrance, Long> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<ParkingEntrance, Car> carCol = new TableColumn<>("CAR");
            carCol.setCellValueFactory(new PropertyValueFactory<>("car"));

            TableColumn<ParkingEntrance, LocalDateTime> entTimeCol = new TableColumn<>("ENTRANCE TIME");
            entTimeCol.setCellValueFactory(new PropertyValueFactory<>("entranceTime"));

            TableColumn<ParkingEntrance, ParkingSpot> spotCol = new TableColumn<>("PARKING SPOT");
            spotCol.setCellValueFactory(new PropertyValueFactory<>("parkingSpot"));

            TableColumn<ParkingEntrance, Boolean> delCol = new TableColumn<>("DELETED");
            delCol.setCellValueFactory(new PropertyValueFactory<>("deleted"));

            entEditTbl.getColumns().addAll(idCol, carCol, entTimeCol, spotCol, delCol);
            entEditTbl.setItems(parkingEntrances);

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }
    }
}
