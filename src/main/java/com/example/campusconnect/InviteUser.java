package com.example.campusconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteUser {
    @FXML private TextField email;

    @FXML
    protected void handleInvite(MouseEvent event) throws IOException {
        try {
            HelloApplication.executeDelete("INSERT INTO Invite VALUES ( '" + email.getText() + "', " + SeeEventsBasic.getCurrId()
                    + ");");
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
    protected void handleBackfromInvite(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SeeRSVPHost.fxml"));
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
