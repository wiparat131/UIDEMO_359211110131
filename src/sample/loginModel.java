package sample;

import dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginModel {
    Connection connection;

    public loginModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (this.connection == null){
            System.exit(1);
        }
    }//loginModel
    public boolean isDatabaseConnection(){
        return this.connection != null;
    }//isDatabaseConnection
    public boolean isLogin(String user,String pass) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;

    String sql = "SELECT * FROM login where username = ? and password = ? and division = ?";
    try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1,user);
            pr.setString(2,pass);
            if (rs.next()) {
                return true;
            }
            return false;
    } catch (SQLException e) {
        if (rs.next()) {
            return true;
        }
        return false;
    }finally {
            pr.close();
            rs.close();
        }

    }//isLogin
    }//class