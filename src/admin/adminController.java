package admin;

import com.jfoenix.controls.JFXButton;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.P;

public abstract class adminController implements Initializable{
    private dbConnection db;
    private ObservableList<StudentData> data;

    @FXML
    private TableView<StudentData> studentTable;

    @FXML
    private TableColumn<StudentData, String> idcolum;

    @FXML
    private TableColumn<StudentData, String> firstnamecolum;

    @FXML
    private TableColumn<StudentData, String> lastnamecolum;

    @FXML
    private TableColumn<StudentData, String> emailcolum;

    @FXML
    private TableColumn<StudentData, String> dobcolum;

    @FXML
    private JFXButton btnload;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new dbConnection();
    }//initialize
    @FXML
    private void loadStudentData(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            //sql
            String sql = "select * from student";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.data.add(new StudentData(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }//while
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //put data to tableview
        this.idcolum.setCellValueFactory(
                new PropertyValueFactory<StudentData,String>("id"));
        this.firstnamecolum.setCellValueFactory(
                new PropertyValueFactory<StudentData,String>("firstName"));
        this.lastnamecolum.setCellValueFactory(
                new PropertyValueFactory<StudentData,String>("lastName"));
        this.emailcolum.setCellValueFactory(
                new PropertyValueFactory<StudentData,String>("email"));
        this.dobcolum.setCellValueFactory(
                new PropertyValueFactory<StudentData,String>("DOB"));
        this.studentTable.setItems(null);
        this.studentTable.setItems(this.data);

    }//loadStudentData

}//class