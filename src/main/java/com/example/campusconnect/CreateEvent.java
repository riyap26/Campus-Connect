package com.example.campusconnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateEvent {
    @FXML private TextField title;
    @FXML private TextArea description;
    @FXML private TextField loc;
    @FXML private TextField time;
    @FXML private TextField cap;
    @FXML private RadioButton invite;
    @FXML private DatePicker date;
    @FXML private ComboBox<String> loc2;

    public static ObservableList<String> locs = FXCollections.observableArrayList("Skiles",
    "Bill Moore", "Tech Tower", "College of Computing", "ISyE Building", "Architecture", "Price Gilbert",
            "Van Leer", "Crossland Tower", "Student Center", "Flag Building", "Ferst Center", "Klaus",
            "Campus Recreation Center", "Clough Library", "Stamps", "Exhibition Hall", "Tech Green");

    @FXML
    protected void initialize() {
        if (loc2 != null) {
            loc2.setItems(locs);
        }
    }


    enum Locate {
        SKILES("Skiles"),
        BILL_MOORE("Bill Moore"),
        TECH_TOWER("Tech Tower"),
        COC("College of Computing"),
        ISyE("ISyE Building"),
        ARCH("Architecture"),
        PG("Price Gilbert"),
        VAN_LEER("Van Leer"),
        CROSS("Crossland Tower"),
        STUDENT_CENTER("Student Center"),
        FLAG("Flag Building"),
        FERST("Ferst Center"),
        KlAUS("Klaus"),
        CRC("Campus Recreation Center"),
        CULC("Clough Library"),
        STAMPS("Stamps"),
        EXHIBIT("Exhibition Hall"),
        TECH_GREEN("TECH GREEN");

        private String game;

        Locate(String game) {
            this.game = game;
        }

        public String GameType() { return game; }

        @Override public String toString() { return game; }

    }


    @FXML
    protected void handleCreate(MouseEvent event) throws IOException {
        try {
            int i = 1;
            if(invite.isSelected()){
                i = 2;
            }
            System.out.println(date.getValue());
            HelloApplication.executeInsert("INSERT INTO Events (title, hostEmail, description, location, date_featured, time_featured, capacity, invite) " +
                    "VALUES ( '" + title.getText() + "', '" + Login.getCurrEmail() + "', '" + description.getText() +
                    "', '" + loc2.getValue() + "', '" + date.getValue() + "', '" + time.getText() + ":00" + "', '" + cap.getText() + "', " + i + ");" );
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationBasic.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene2);
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    protected void handleBackfromCreate(MouseEvent event) throws IOException {
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
