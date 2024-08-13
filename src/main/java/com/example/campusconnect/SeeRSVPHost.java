package com.example.campusconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SeeRSVPHost {

    @FXML private RadioButton yes;
    @FXML private RadioButton maybe;
    @FXML private RadioButton no;
    @FXML private RadioButton ihateu;

    @FXML private TableView rsvpTable;
    @FXML private TableColumn<Name, CheckBoxTableCell> nameCheck;
    @FXML private TableColumn<Name, String> name;
    int rsvp;


    @FXML
    public void initialize() {
        final ToggleGroup group = new ToggleGroup();
        yes.setToggleGroup(group);
        maybe.setToggleGroup(group);
        no.setToggleGroup(group);
        ihateu.setToggleGroup(group);
    }

    public class Name {
        private CheckBox select;
        private String name;


        public Name(String name) {
            this.select = new CheckBox();
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public CheckBox getSelect() {return select;}
        public void setSelect(CheckBox select) {
            this.select = select;
        }
    }

    @FXML
    protected void searchRSVP(MouseEvent event) {
        try {
            rsvpTable.getItems().clear();
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameCheck.setCellValueFactory(new PropertyValueFactory<>("select"));
            if (yes.isSelected()) {
                rsvp = 4;
            } else if (maybe.isSelected()) {
                rsvp = 3;
            } else if (no.isSelected()) {
                rsvp = 2;
            } else if (ihateu.isSelected()) {
                rsvp = 1;
            }
            HelloApplication.executeInsert("DROP VIEW IF EXISTS BrowseRSVP;");
            HelloApplication.executeInsert("CREATE VIEW BrowseRSVP (email, id, rsvp, name) AS SELECT useremail, rid, rsvp, name  FROM RSVP r INNER JOIN Users u ON r.useremail = u.email;");

            ResultSet rs3 = HelloApplication.executeSelect("SELECT ID FROM Events WHERE title= '"
                    + SeeEventsBasic.getCurrTitle() + "';");
            rs3.next();
            int id = Integer.parseInt(rs3.getString(1));

            ResultSet rs = HelloApplication.executeSelect("SELECT name FROM BrowseRSVP WHERE id = " + id + " AND  rsvp = " + rsvp + ";");
            while (rs.next()) {
                rsvpTable.getItems().addAll(new SeeRSVPHost.Name(rs.getString(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    protected void deleteRsvp(MouseEvent event) throws IOException {
        try {
            ResultSet rs3 = HelloApplication.executeSelect("SELECT ID FROM Events WHERE title= '"
                    + SeeEventsBasic.getCurrTitle() + "';");
            rs3.next();
            int id = Integer.parseInt(rs3.getString(1));
            for (Object o : rsvpTable.getItems()) {
                Name p = (Name) o;
                HelloApplication.executeInsert("DROP VIEW IF EXISTS BrowseRSVP;");
                HelloApplication.executeInsert("CREATE VIEW BrowseRSVP (email, id, rsvp, name) AS SELECT useremail, rid, rsvp, name  FROM RSVP r INNER JOIN Users u ON r.useremail = u.email;");
                ResultSet rs = HelloApplication.executeSelect("SELECT email FROM BrowseRSVP WHERE id = " + id + " AND  rsvp = " + rsvp + " AND name = '" + p.getName() +"';");
                if (p.getSelect().isSelected()) {
                    if (rs.next()) {
                        HelloApplication.executeInsert("DELETE FROM RSVP WHERE rid = " + id + "  AND useremail = '" + rs.getString(1) +  "';");
                        ResultSet rs1 = HelloApplication.executeSelect("SELECT attending FROM Events WHERE ID = "
                                + id + ";");
                        if(rsvp > 2 && rs1.next()) {
                            HelloApplication.executeInsert("UPDATE Events SET attending = "
                                    + (Integer.parseInt(rs1.getString(1)) - 1) + " WHERE ID = " + id + ";");
                        }
                    }
                }
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SeeRSVPHost.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene2);
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    protected void handleBack(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("myviewBasic.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    protected void handleInvite(MouseEvent event) throws IOException {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inviteUser.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e);
    }
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
