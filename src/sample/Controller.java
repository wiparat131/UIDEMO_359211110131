package sample;

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
                    (this.loginModel.isLogin(this.username.getText(),this.password.getText(),
                    this.combobox.getValue().toString())){
                Stage stage = (Stage) this.btnLogin.getScene().getWindow();
                stage.close();
                switch (this.combobox.getValue().toString()){
                    case "Admin": adminLogin(); break;
                    case "Student": studentLogin(); break;
                }
            }else{
                this.loginStatus.setText("Wrong Credential");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
    public void studentLogin(){
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)
                    loader.load(getClass().getResource("/Students/StudentFXML.fxml").openStream
                            ());
            StudentsController studentsController = (StudentsController)
                    loader.getController();
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student Dashboard");
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void adminLogin(){
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminroot = (Pane)
                    adminLoader.load(getClass().getResource("/Admin/Admin.fxml").openStream());
            AdminController adminController = (AdminController)
                    adminLoader.getController();
            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

}//class
