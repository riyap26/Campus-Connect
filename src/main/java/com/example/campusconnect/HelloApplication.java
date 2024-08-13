package com.example.campusconnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    public static Connection con;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/campus_connect", "root", "12345678");
            launch();
            con.close();
        }
        catch (Exception e) {System.out.println(e);}
    }

    public static ResultSet executeSelect(String qry) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(qry);
        return(rs);
    }

    public static void executeInsert(String qry) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate(qry);
    }
    public static void executeDelete(String qry) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate(qry);
    }
}