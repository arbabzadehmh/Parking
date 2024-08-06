package parking.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import parking.controller.BaseController;
import parking.controller.CostRateController;
import parking.controller.UserController;
import parking.model.entity.CostRate;
import parking.model.entity.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ParkingLoginViewController implements Initializable {

    @FXML
    private TextField userNameTxt;

    @FXML
    private PasswordField passTxt;

    @FXML
    private Button loginBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loginBtn.setOnAction(event -> {
            try {
                User user = UserController.login(userNameTxt.getText(), passTxt.getText());
                if (user != null){
                    BaseController.user = user;
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ParkingMainView.fxml")));
                    stage.setScene(scene);
                    stage.setTitle("Main");
                    stage.show();
                    loginBtn.getScene().getWindow().hide();
                    if (CostRateController.find() != null){
                        BaseController.costRate = CostRateController.find();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "The Cost Rate is not set !", ButtonType.OK);
                        alert.show();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Access Denied !", ButtonType.OK);
                    alert.show();
                }
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
            }
        });

    }
}
