package com.example.campusconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewEventAdmin {

    @FXML private Text title;
    @FXML private Text host;
    @FXML private Text loc;
    @FXML private Text time;
    @FXML private Text max;
    @FXML private Text attend;
    @FXML private Text in;
    @FXML private TextFlow descript;
    @FXML
    public void initialize() {
        if (title != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT title FROM Events WHERE title ='" +SeeEventsAdmin.getCurrTitle() +"';");
                rs.next();
                title.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (host != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT name FROM Users WHERE email IN (SELECT hostEmail FROM Events " +
                                "WHERE title = '" + SeeEventsAdmin.getCurrTitle()+ "');");
                rs.next();
                host.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (time != null) {
            ResultSet rs;
            ResultSet rs2;
            try {
                rs = HelloApplication.executeSelect("SELECT date_featured FROM Events WHERE title ='" +SeeEventsAdmin.getCurrTitle() +"';");
                rs.next();
                rs2 = HelloApplication.executeSelect("SELECT time_featured FROM Events WHERE title ='" +SeeEventsAdmin.getCurrTitle() +"';");
                rs2.next();
                time.setText(rs.getString(1) + " " + rs2.getString(1).substring(0,5));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
       if (loc != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT location FROM Events WHERE title ='" + SeeEventsAdmin.getCurrTitle() +"';");
                rs.next();
                loc.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        if (descript != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT description FROM Events WHERE title ='" +SeeEventsAdmin.getCurrTitle() +"';");
                rs.next();
                Text t1 = new Text(rs.getString(1));
                descript.getChildren().add(t1);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (max != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT capacity FROM Events WHERE title ='" +SeeEventsAdmin.getCurrTitle() +"';");
                rs.next();
                max.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (attend != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT attending FROM Events WHERE title ='" +SeeEventsAdmin.getCurrTitle() +"';");
                if (rs.next()) {
                    attend.setText(rs.getString(1));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (in != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT invite FROM Events WHERE title = '" +SeeEventsAdmin.getCurrTitle() +"';");
                rs.next();
                int i = Integer.parseInt(rs.getString(1));
                if (i == 2) {
                    in.setText("An invite is required");
                } else {
                    in.setText("No invite required");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    protected void handleRSVP(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SeeRSVPAdmin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    protected void handleDeleteEvent(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("removeAdmin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Delete");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    protected void handleBackEvents(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationAdmin.fxml"));
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
