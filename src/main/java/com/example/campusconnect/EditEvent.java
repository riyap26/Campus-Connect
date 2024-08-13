package com.example.campusconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditEvent {
    @FXML private TextField title;
    @FXML private TextField loc;
    @FXML private TextField time;
    @FXML private TextArea descript;
    @FXML private TextField cap;
    @FXML private RadioButton invite;
    @FXML private DatePicker date;
    @FXML private ComboBox<String> loc2;

    @FXML
    public void initialize() {
        if (title != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT title FROM Events WHERE ID = " +SeeEventsBasic.getCurrId() +";");
                rs.next();
                title.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (time != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT time_featured FROM Events WHERE ID = " +SeeEventsBasic.getCurrId() +";");
                rs.next();
                time.setText(rs.getString(1).substring(0,5));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (loc2 != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT location FROM Events WHERE ID = " +SeeEventsBasic.getCurrId() +";");
                rs.next();
                loc2.setValue((String) rs.getString(1));
                loc2.setItems(CreateEvent.locs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (descript != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT description FROM Events WHERE ID = " +SeeEventsBasic.getCurrId() +";");
                rs.next();
                descript.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (cap != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT capacity FROM Events WHERE ID = " +SeeEventsBasic.getCurrId() +";");
                rs.next();
                cap.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (invite != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT invite FROM Events WHERE ID = " +SeeEventsBasic.getCurrId() +";");
                rs.next();
                int i = Integer.parseInt(rs.getString(1));
                if (i == 2) {
                    invite.setSelected(true);
                } else {
                    invite.setSelected(false);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (date != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT date_featured FROM Events WHERE ID = " +SeeEventsBasic.getCurrId() +";");
                rs.next();
                date.setValue(LocalDate.parse(rs.getString(1)));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    protected void handleEdit(MouseEvent event) throws IOException {
        try {
            int i = 1;
            if(invite.isSelected()){
                i = 2;
            }
            HelloApplication.executeInsert("UPDATE Events SET title = '" + title.getText() + "', date_featured = '"
                    + date.getValue() + "', time_featured = '" + time.getText() + ":00" + "', location = '" + loc2.getValue() + "', description = '" + descript.getText() +
                    "', capacity = " + cap.getText() + ", invite = " + i +
                    " WHERE ID = " + SeeEventsBasic.getCurrId() + ";" );
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationBasic.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene2);
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    protected void handleBackfromEdit(MouseEvent event) throws IOException {
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
