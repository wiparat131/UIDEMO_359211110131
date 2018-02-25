package sample;

import admin.adminController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
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

        @FXML
        private Label loginStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.loginmodel.isDatabaseConnection()){
            this.dbStatus.setText("Connection to DB.");
        }else {
            this.dbStatus.setText("Not Connect to DB.");
        }

    }//initialize
    @FXML
    public void Login (ActionEvent event){
        try {
            if
                    (this.loginmodel.isLogin(this.username.getText(),this.password.getText()
                    )){
                Stage stage = (Stage) this.btnLogin.getScene().getWindow();
                stage.close();

            }else{
                this.loginStatus.setText("Wrong Credential");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }//Login

    private void adminDashboard() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = (Pane) adminLoader.load(
                    getClass().getResource("/admin/adminDashboard.fxml").openStream());
            adminController adminController = adminLoader.getController();
            Scene scene = new Scene(adminRoot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//adminDashboard

    public void setLoginStatus(Label loginStatus) {
        this.loginStatus = loginStatus;
    }
}//class