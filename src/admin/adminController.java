package admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    @FXML
    private TextField searchTxt;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtfistname;

    @FXML
    private JFXTextField txtlastname;

    @FXML
    private JFXTextField txtemail;

    @FXML
    private DatePicker txtDOB;


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



        //Filter Data in TableView
        FilteredList<StudentData> filteredData =
                new FilteredList<>(data, e -> true);
        searchTxt.setOnKeyReleased(e -> {
            searchTxt.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        filteredData.setPredicate(StudentData -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();
                            if (StudentData.getId().contains(newValue)) {
                                return true;
                            } else if
                                    (StudentData.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if
                                    (StudentData.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            }
                            return false;
                        });
                    });
            SortedList<StudentData> sortedData =
                    new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(
                    studentTable.comparatorProperty());
            studentTable.setItems(sortedData);

        });
    }//Load StudentData
    private void addStudent(ActionEvent event) {
        String sqlInsert = ""+"insert into student (ID,fistname,"+"lastname,email,DOB)valuse(?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(sqlInsert);
            pr.setString(1, this.txtID.getText());
            pr.setString(2, this.txtfistname.getText());
            pr.setString(3, this.txtlastname.getText());
            pr.setString(4, this.txtemail.getText());
            pr.setString(5, this.txtDOB.getEditor().getText());

            pr.execute();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadStudentData(new ActionEvent());
    }//addStudent

    @FXML
    private void clearForm(ActionEvent event) {
        this.txtID.setText("");
        this.txtfistname.setText("");
        this.txtlastname.setText("");
        this.txtemail.setText("");
        this.txtDOB.setValue(null);
    }//ClearForm

    @FXML
    private void deleteStudent(ActionEvent event){
        //get data from TableStudent
        StudentData std = studentTable.getSelectionModel().getSelectedItem();
        //DialogMessage
        JOptionPane.showConfirmDialog(null,"Do you want to Delete student name: "
                +std.getFirstName()+" "+std.getLastName());
        //Delete
        if (std != null){
            String sqlDelete = "Delete From student where ID = ?";
            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement pr = conn.prepareStatement(sqlDelete);
                pr.setString(1,std.getId());
                pr.executeUpdate();
                pr.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.exit(1);
        }
        loadStudentData(new ActionEvent());

    }//DeleteStudent

    @FXML
    private void editStudent(ActionEvent event) {
        StudentData std = studentTable.getSelectionModel().getSelectedItem();
        if (std !=null){
            txtID.setText(std.getId());
            //disable TextFiled to read only
            txtID.setDisable(true);
            txtfistname.setText(std.getFirstName());
            txtlastname.setText(std.getLastName());
            txtemail.setText(std.getEmail());
            txtDOB.getEditor().setText(std.getDOB());
        }
    }//editStudent
    @FXML
    private void saveStudent(ActionEvent event) {
        StudentData std = studentTable.getSelectionModel().getSelectedItem();
        //DialogMessage
        JOptionPane.showConfirmDialog(null,"Do you want to Update student name: "
                +std.getFirstName()+" "+std.getLastName());
        String sqlUpdate = "update student set firstname = ?,"+"lastname = ?,,email = ?,DOB = ? where ID = ?";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(sqlUpdate);
            pr.setString(1,this.txtfistname.getText());
            pr.setString(2,this.txtlastname.getText());
            pr.setString(3,this.txtemail.getText());
            pr.setString(4,this.txtDOB.getEditor().getText());
            pr.setString(5,this.txtID.getText());

            pr.executeUpdate();
            pr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadStudentData(new ActionEvent());

    }//SaveStudent

    @FXML
    private void logout(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        State primaryStage = new State();
        Main m = new Main();
        m.start(primaryStage);
    }//logout


}//class