package com.example.campusconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewEventBasic {
    @FXML private Text title;
    @FXML private Text host;
    @FXML private Text loc;
    @FXML private Text time;
    @FXML private Text max;
    @FXML private Text attend;
    @FXML private Text in;
    @FXML private TextFlow descript;
    @FXML private RadioButton yes;
    @FXML private RadioButton maybe;
    @FXML private RadioButton no;
    @FXML private RadioButton ihateu;
    int rsvp = 0;
    int rsvpN = 0;
    int invite;


    @FXML
    public void initialize() {
        final ToggleGroup group = new ToggleGroup();
        yes.setToggleGroup(group);
        maybe.setToggleGroup(group);
        no.setToggleGroup(group);
        ihateu.setToggleGroup(group);

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
        if (host != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT name FROM Users WHERE email IN (SELECT hostEmail FROM Events " +
                        "WHERE title = '" + SeeEventsBasic.getCurrTitle()+ "');");
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
        if (max != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT capacity FROM Events WHERE title ='" +SeeEventsBasic.getCurrTitle() +"';");
                rs.next();
                max.setText(rs.getString(1));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (attend != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT attending FROM Events WHERE title ='" +SeeEventsBasic.getCurrTitle() +"';");
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
                rs = HelloApplication.executeSelect("SELECT invite FROM Events WHERE title = '" +SeeEventsBasic.getCurrTitle() +"';");
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
        if (group != null) {
            ResultSet rs;
            try {
                rs = HelloApplication.executeSelect("SELECT rsvp FROM RSVP WHERE rid = " + SeeEventsBasic.getCurrId()
                        + " AND useremail = '" + Login.getCurrEmail() +  "' ;");
                if (rs.next()) {
                    rsvp = Integer.parseInt(rs.getString(1));
                }
                switch(rsvp) {
                    case 4:
                        yes.setSelected(true);
                        break;
                    case 3:
                        maybe.setSelected(true);
                        break;
                    case 2:
                        no.setSelected(true);
                        break;
                    case 1:
                        ihateu.setSelected(true);
                        break;
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    @FXML
    protected void handleUpdate(MouseEvent event) throws IOException {
        try{
            // if no prexisting rsvp
            ResultSet rs3 = HelloApplication.executeSelect("SELECT ID FROM Events WHERE title= '"
                    + SeeEventsBasic.getCurrTitle() + "';");
            rs3.next();
            int id = Integer.parseInt(rs3.getString(1));
            ResultSet rs1 = HelloApplication.executeSelect("SELECT attending FROM Events WHERE ID = "
                    + id + ";");
            ResultSet rs2 = HelloApplication.executeSelect("SELECT capacity FROM Events WHERE ID = "
                    + id + ";");
            rs2.next();
            rs1.next();
            //if (Integer.parseInt(rs1.getString(1)) + 1 <= Integer.parseInt(rs2.getString(1))) {
                if (rsvp == 0) {
                    ResultSet rs = HelloApplication.executeSelect("SELECT invite FROM Events WHERE title = '"
                            + SeeEventsBasic.getCurrTitle() + "';");
                    if (rs.next()) {
                        invite = Integer.parseInt(rs.getString(1));
                        ResultSet rs4 = HelloApplication.executeSelect("SELECT useremail FROM Invite WHERE iid= "
                                + id + " AND useremail = '" + Login.getCurrEmail() + "';");

                        if (invite == 1 || rs4.next()) {
                            if (yes.isSelected()) {
                                rsvp = 4;
                            } else if (maybe.isSelected()) {
                                rsvp = 3;
                            } else if (no.isSelected()) {
                                rsvp = 2;
                            } else if (ihateu.isSelected()) {
                                rsvp = 1;
                            }
                            if (rs1.getString(1) == null) {
                                HelloApplication.executeInsert("INSERT INTO RSVP (useremail, rid, rsvp) Values ('" + Login.getCurrEmail() +
                                        "', " + id + ", " + rsvp + ");");
                                if (rsvp > 2) {
                                    HelloApplication.executeInsert("UPDATE Events SET attending = "
                                            +  1 + " WHERE ID = " + id + ";");
                                }
                                System.out.println("Hewo");
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewEventBasic.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Home");
                                Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                                stage.setScene(scene2);
                                stage.show();
                                ((Node) event.getSource()).getScene().getWindow().hide();
                            } else {
                                if (Integer.parseInt(rs1.getString(1)) + 1 <= Integer.parseInt(rs2.getString(1))) {
                                    HelloApplication.executeInsert("INSERT INTO RSVP Values ('" + Login.getCurrEmail() +
                                            "', " + id + ", " + rsvp + ");");
                                    if (rsvp > 2) {
                                        HelloApplication.executeInsert("UPDATE Events SET attending = "
                                                + (Integer.parseInt(rs1.getString(1)) + 1) + " WHERE ID = " + id + ";");
                                    }
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewEventBasic.fxml"));
                                    Stage stage = new Stage();
                                    stage.setTitle("Home");
                                    Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                                    stage.setScene(scene2);
                                    stage.show();
                                    ((Node) event.getSource()).getScene().getWindow().hide();
                                }
                            }
                        }
                    }
                } else {
                    ResultSet rs = HelloApplication.executeSelect("SELECT invite FROM Events WHERE ID= "
                            + id + ";");
                    if (rs.next()) {
                        invite = Integer.parseInt(rs.getString(1));
                        ResultSet rs4 = HelloApplication.executeSelect("SELECT useremail FROM Invite WHERE iid= "
                                + id + " AND useremail = '" + Login.getCurrEmail() + "';");
                        if (invite == 1 || rs4.next()) {
                            if (yes.isSelected()) {
                                rsvpN = 4;
                            } else if (maybe.isSelected()) {
                                rsvpN = 3;
                            } else if (no.isSelected()) {
                                rsvpN = 2;
                            } else if (ihateu.isSelected()) {
                                rsvpN = 1;
                            }
                            if (Integer.parseInt(rs1.getString(1)) + 1 <= Integer.parseInt(rs2.getString(1))) {
                                HelloApplication.executeInsert("UPDATE RSVP SET rsvp = " + rsvpN + " WHERE rid = " +id + " AND useremail = '"
                                       +Login.getCurrEmail() +  "';");
                                if (rsvp < 3 && rsvpN > 2) {
                                    HelloApplication.executeInsert("UPDATE Events SET attending = "
                                            + (Integer.parseInt(rs1.getString(1)) + 1) + " WHERE ID = " + id + ";");
                                } else if (rsvp > 2 && rsvpN < 3) {
                                    HelloApplication.executeInsert("UPDATE Events SET attending = "
                                            + (Integer.parseInt(rs1.getString(1)) - 1) + " WHERE ID = " + id + ";");
                                }
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewEventBasic.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Home");
                                Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                                stage.setScene(scene2);
                                stage.show();
                                ((Node) event.getSource()).getScene().getWindow().hide();
                            }
                        }
                    }
                }
            //}
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    protected void handleDelete(MouseEvent event) throws IOException {
        try{
            ResultSet rs = HelloApplication.executeSelect("SELECT ID FROM Events WHERE title= '"
                    + SeeEventsBasic.getCurrTitle() + "';");
            rs.next();
            int id = Integer.parseInt(rs.getString(1));
            if (rsvp != 0) {
                HelloApplication.executeInsert("DELETE FROM RSVP WHERE useremail = '" +  Login.getCurrEmail() + "' AND rid = " + id +";");
            } if (rsvp > 2) {
                ResultSet rs1 = HelloApplication.executeSelect("SELECT attending FROM Events WHERE ID= "
                        + id + ";");
                rs1.next();
                HelloApplication.executeInsert("UPDATE Events SET attending = "
                        + (Integer.parseInt(rs1.getString(1)) - 1) + " WHERE ID = " + id + ";");
            }
        } catch(Exception e){
        System.out.println(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewEventBasic.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    protected void handleRSVP(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SeeRSVPBasic.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    protected void handleBack(MouseEvent event) throws IOException {
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
