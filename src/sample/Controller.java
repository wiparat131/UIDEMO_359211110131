package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public loginModel loginmodel = new loginModel();

    public Controller() {
    }
        @FXML
        private JFXTextField username;

        @FXML
        private JFXTextField password;

        @FXML
        private JFXButton btnLogin;

        @FXML
        private Label dbStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.loginmodel.isDatabaseConnection()){
            this.dbStatus.setText("Connection to DB.");
        }else {
            this.dbStatus.setText("Not Connect to DB.");
        }

    }

}//class
