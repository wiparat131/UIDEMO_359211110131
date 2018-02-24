package sample;

import admin.adminController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private loginModel loginModel = new loginModel();

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Label loginStatus;

    @FXML
    private Label dbStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (this.loginModel.isDatabaseConnection()) {
            this.dbStatus.setText("Connected to DB.");
        }else {
            this.dbStatus.setText("Not Connect to DB.");
        }
    }//initialize

    @FXML
    public void Login(ActionEvent event) {
        try {
            if (this.loginModel.isLogin(username.getText(),password.getText())){
                Stage stage = (Stage) this.btnLogin.getScene().getWindow();
                stage.close();
                adminDashboard();

            }else {
                loginStatus.setText("Your username or password is invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

}//class