package com.example.campusconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveBasic {
    @FXML private Text title;
    @FXML private Text loc;
    @FXML private Text time;
    @FXML private TextFlow descript;
    @FXML
    public void initialize() {
        if (title != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT title FROM Events WHERE title ='" +SeeEventsBasic.getCurrTitle() +"';");
                rs.next();
                title.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (time != null) {
            ResultSet rs;
            ResultSet rs2;
            try {
                rs = HelloApplication.executeSelect("SELECT date_featured FROM Events WHERE title ='" +SeeEventsBasic.getCurrTitle() +"';");
                rs.next();
                rs2 = HelloApplication.executeSelect("SELECT time_featured FROM Events WHERE title ='" +SeeEventsBasic.getCurrTitle() +"';");
                rs2.next();
                time.setText(rs.getString(1) + " " + rs2.getString(1).substring(0,5));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (loc != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT location FROM Events WHERE title ='" + SeeEventsBasic.getCurrTitle() +"';");
                rs.next();
                loc.setText((String)rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (descript != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT description FROM Events WHERE title ='" +SeeEventsBasic.getCurrTitle() +"';");
                rs.next();
                Text t1 = new Text(rs.getString(1));
                descript.getChildren().add(t1);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    protected void handleDelete(MouseEvent event) throws IOException {
        try {
            HelloApplication.executeDelete("DELETE FROM Events WHERE title = '" + SeeEventsBasic.getCurrTitle() +"';");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationBasic.fxml"));
            Stage stage = new Stage();
            stage.setTitle("SeeAll");
            Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene2);
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    protected void handleBackfromDelete(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationBasic.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    protected void handleLogoff(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
