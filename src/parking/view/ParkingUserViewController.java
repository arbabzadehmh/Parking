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
import parking.controller.UserController;

import parking.model.entity.User;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ParkingUserViewController implements Initializable {

    @FXML
    private TextField idTxt, nameTxt, familyTxt, usernameTxt, passwordTxt, codeTxt;

    @FXML
    private Label idLbl, nameLbl, familyLbl, usernameLbl, passwordLbl, codeLbl, statusLbl, delLbl, userTitleLbl, userLbl;

    @FXML
    private Shape lineShape;

    @FXML
    private Pane userPane;

    @FXML
    private TableView<User> userTbl;

    @FXML
    private ComboBox<String> statusCmb, delCmb;

    @FXML
    private Button saveBtn, findAllBtn, clearBtn, idFindBtn, nameFindBtn, familyFindBtn, usernameFindBtn, statusFindBtn, backBtn, exitBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        statusCmb.getItems().add("Active");
        statusCmb.getItems().add("Inactive");
        
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
                BaseController.formType = FormType.User;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingModifyPartsView.fxml")));
                stage.setScene(scene);
                stage.setTitle("User Modify");
                stage.show();
                backBtn.getScene().getWindow().hide();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.show();
            }
        });

        userTbl.setOnMouseClicked((event) -> {
            User user = userTbl.getSelectionModel().getSelectedItem();
            if (user != null){
                selectTableRow(user);
            }
        });

        userTbl.setOnKeyReleased((event) -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
                User user = userTbl.getSelectionModel().getSelectedItem();
                if (user != null) {
                    selectTableRow(user);
                }
            }
        });

        if (BaseController.formType.equals(FormType.User_save)){

            userTitleLbl.setVisible(false);
            findAllBtn.setVisible(false);
            idFindBtn.setVisible(false);
            nameFindBtn.setVisible(false);
            familyFindBtn.setVisible(false);
            usernameFindBtn.setVisible(false);
            statusFindBtn.setVisible(false);
            statusCmb.setVisible(false);
            statusLbl.setVisible(false);
            delCmb.setVisible(false);
            delLbl.setVisible(false);
            lineShape.setVisible(false);
            userTbl.setVisible(false);
            idTxt.setVisible(false);
            idLbl.setVisible(false);
            userPane.setPrefWidth(430);
            userPane.setPrefHeight(580);
            userLbl.setLayoutX(200);
            backBtn.setLayoutX(65);
            backBtn.setLayoutY(495);
            exitBtn.setLayoutX(286);
            exitBtn.setLayoutY(495);
            saveBtn.setLayoutY(430);
            clearBtn.setLayoutY(430);

            saveBtn.setOnAction(event -> {
                Map<String, String> result = UserController.save(nameTxt.getText(), familyTxt.getText(), usernameTxt.getText(), passwordTxt.getText(), codeTxt.getText());
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });
        } else if (BaseController.formType.equals(FormType.User_edit)) {

            saveBtn.setText("Edit");
            showAllDataOnTable();

            saveBtn.setOnAction(event -> {
                boolean status = statusCmb.getSelectionModel().getSelectedItem().equals("Active");
                boolean deleted = delCmb.getSelectionModel().getSelectedItem().equals("Yes");
                Map<String, String> result = UserController.edit(Long.parseLong(idTxt.getText()), nameTxt.getText(), familyTxt.getText(), usernameTxt.getText(), passwordTxt.getText(), codeTxt.getText(), status, deleted);
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });

        } else if (BaseController.formType.equals(FormType.User_remove)) {

            saveBtn.setText("Remove");
            showAllDataOnTable();

            nameFindBtn.setVisible(false);
            familyFindBtn.setVisible(false);
            usernameFindBtn.setVisible(false);
            statusFindBtn.setVisible(false);
            statusCmb.setVisible(false);
            statusLbl.setVisible(false);
            delCmb.setVisible(false);
            delLbl.setVisible(false);
            nameTxt.setVisible(false);
            nameLbl.setVisible(false);
            familyTxt.setVisible(false);
            familyLbl.setVisible(false);
            usernameLbl.setVisible(false);
            usernameTxt.setVisible(false);
            passwordTxt.setVisible(false);
            passwordLbl.setVisible(false);
            codeTxt.setVisible(false);
            codeLbl.setVisible(false);

            saveBtn.setOnAction(event -> {
                Map<String, String> result = UserController.remove(Long.parseLong(idTxt.getText()));
                if (result.get("status").equals("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, result.get("message"), ButtonType.OK);
                    alert.show();
                    showAllDataOnTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, result.get("message"), ButtonType.CANCEL);
                    alert.show();
                }
            });
        } else if (BaseController.formType.equals(FormType.User_find)) {
            saveBtn.setVisible(false);
            delCmb.setVisible(false);
            delLbl.setVisible(false);
            passwordTxt.setVisible(false);
            passwordLbl.setVisible(false);
            codeTxt.setVisible(false);
            codeLbl.setVisible(false);
            findAllBtn.setLayoutX(65);
        }

        findAllBtn.setOnAction(event -> {
            showAllDataOnTable();
        });

        idFindBtn.setOnAction(event -> {
            Map<String, List<User>> result = UserController.findById(Long.parseLong(idTxt.getText()));
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        nameFindBtn.setOnAction(event -> {
            Map<String, List<User>> result = UserController.findByName(nameTxt.getText());
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        familyFindBtn.setOnAction(event -> {
            Map<String, List<User>> result = UserController.findByFamily(familyTxt.getText());
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        usernameFindBtn.setOnAction(event -> {
            Map<String, List<User>> result = UserController.findByUserName(usernameTxt.getText());
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

        statusFindBtn.setOnAction(event -> {
            boolean status = statusCmb.getSelectionModel().getSelectedItem().equals("Active");
            Map<String, List<User>> result = UserController.findByStatus(status);
            if (result.containsKey("ok")){
                showDataOnTable(result.get("ok"));
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, result.toString(), ButtonType.CANCEL);
                alert.show();
                resetForm();
            }
        });

    }

    private void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        usernameTxt.clear();
        passwordTxt.clear();
        codeTxt.clear();
        statusCmb.getSelectionModel().clearSelection();
        delCmb.getSelectionModel().clearSelection();
        userTbl.getColumns().clear();
    }

    private void selectTableRow(User user) {
        idTxt.setText(String.valueOf(user.getId()));
        nameTxt.setText(user.getName());
        familyTxt.setText(user.getFamily());
        usernameTxt.setText(user.getUserName());
        passwordTxt.setText(user.getPassword());
        codeTxt.setText(user.getNationalCode());
        if (user.isStatus()){
            statusCmb.setValue("Active");
        }else {
            statusCmb.setValue("Inactive");
        }
        if (user.isDeleted()){
            delCmb.setValue("Yes");
        }else {
            delCmb.setValue("No");
        }
    }

    private void showAllDataOnTable(){
        try {
            Map<String, List<User>> result = UserController.findAll();
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

    private void showDataOnTable(List<User> userList){

        userTbl.getColumns().clear();

        try {
            ObservableList<User> users = FXCollections.observableList(userList);

            TableColumn<User, Long> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<User, String> nameCol = new TableColumn<>("NAME");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<User, String> familyCol = new TableColumn<>("FAMILY");
            familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));

            TableColumn<User, String> userCol = new TableColumn<>("USERNAME");
            userCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

            TableColumn<User, String> passCol = new TableColumn<>("PASSWORD");
            passCol.setCellValueFactory(new PropertyValueFactory<>("password"));

            TableColumn<User, String> codeCol = new TableColumn<>("NATIONAL CODE");
            codeCol.setCellValueFactory(new PropertyValueFactory<>("nationalCode"));

            TableColumn<User, Boolean> statusCol = new TableColumn<>("STATUS");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            TableColumn<User, Boolean> deletedCol = new TableColumn<>("DELETED");
            deletedCol.setCellValueFactory(new PropertyValueFactory<>("deleted"));

            userTbl.getColumns().addAll(idCol, nameCol, familyCol, userCol, passCol, codeCol, statusCol, deletedCol);
            userTbl.setItems(users);

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.show();
        }

    }
}
