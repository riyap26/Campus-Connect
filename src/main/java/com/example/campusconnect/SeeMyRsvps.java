package com.example.campusconnect;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class SeeMyRsvps implements Initializable {

    public static String currTitle;
    @FXML public static Label editSaved;
    public static Text rvcon;

    public static String getCurrTitle() {
        return currTitle;
    }

    public static void setCurrTitle(String s) {
        currTitle = s;
    }

    public static int currID;
    public static int getCurrId() {
        return currID;
    }
    protected static void setCurrId(int id) { currID = id; }

    public static int countId;
    protected static int getCountId() {
        return countId;
    }
    protected static void setCountId(int id) { countId = id; }

    public static String sHost;
    public static String getsHost() {
        return sHost;
    }
    public static void setsHost(String s) { sHost = s; }

    public static String sLoc;
    public static String getsLoc() {
        return sLoc;
    }
    public static void setsLoc(String s) { sLoc = s; }

    public static LocalDate seDate;
    public static LocalDate getseDate() {
        return seDate;
    }
    public static void setseDate(LocalDate s) { seDate = s; }


    public static void setCurrTitle(int id) {
        currID = id;
    }



    private final static int dataSize = 100;

    @FXML private TableView myRsvps;
    @FXML private TableColumn<RSVPS, String> myEvents;
    @FXML private TableColumn<RSVPS, LocalDate> myDate;
    @FXML private TableColumn<RSVPS, LocalTime> myTime;





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (myRsvps != null) {
            try {
                int k = 1;
                myEvents.setCellValueFactory(new PropertyValueFactory<>("e"));
                myDate.setCellValueFactory(new PropertyValueFactory<>("d"));
                myTime.setCellValueFactory(new PropertyValueFactory<>("t"));
                HelloApplication.executeInsert("DROP VIEW IF EXISTS MyRsvps;");
                HelloApplication.executeInsert("CREATE VIEW MyRsvps (ID, title, date_featured, time_featured, rsvp) AS SELECT ID, title, date_featured, time_featured, rsvp FROM (Events E INNER JOIN RSVP RV ON E.ID = RV.rid) WHERE RV.useremail = '" + Login.getCurrEmail() + "';");
                ResultSet rs = HelloApplication.executeSelect("SELECT title FROM MyRsvps");
                ArrayList<String> arr = new ArrayList<>();
                while(rs.next()) {
                    arr.add(rs.getString(1));
                }
                if (arr.size() > 1) {
                    ArrayList<LocalDate> dat = new ArrayList<>();
                    ArrayList<LocalTime> tim = new ArrayList<>();
                    ArrayList<Integer> rsvp = new ArrayList<>();
                    for (int i = 0; i < arr.size(); i++) {
                        ResultSet r = HelloApplication.executeSelect("SELECT date_featured FROM Events WHERE title = '" + arr.get(i) + "';");
                        ResultSet r1 = HelloApplication.executeSelect("SELECT time_featured FROM Events WHERE title = '" + arr.get(i) + "';");
                        ResultSet r2 = HelloApplication.executeSelect("SELECT rsvp FROM MyRsvps WHERE title = '" + arr.get(i) + "';");
                        if (r.next() && r1.next() &&  r2.next()) {
                            dat.add(LocalDate.parse(r.getString(1)));
                            tim.add(LocalTime.parse(r1.getString(1)));
                            rsvp.add(Integer.parseInt(r2.getString(1)));
                        }
                    }
                    ArrayList<String> cool = new ArrayList<>();
                    ArrayList<String> conflict = new ArrayList<>();
                    ArrayList<LocalDate> cooldat = new ArrayList<>();
                    ArrayList<LocalTime> cooltim = new ArrayList<>();
                    ArrayList<LocalDate> condat = new ArrayList<>();
                    ArrayList<LocalTime> contim = new ArrayList<>();
                    System.out.println(dat);
                    System.out.println(tim);
                    for (int i = 0; i < arr.size() - 1; i++) {
                        System.out.println(i);
                        System.out.println(cool);
                        for (int j = i+1; j < arr.size(); j++) {
                            if (dat.get(i).equals(dat.get(j)) && tim.get(i).equals(tim.get(j)) && !(rsvp.get(i) == 2 || rsvp.get(j) == 2)) {
                                if (!conflict.contains(arr.get(i))) {
                                    conflict.add(arr.get(i));
                                    condat.add(dat.get(i));
                                    contim.add(tim.get(i));
                                }
                                if (!conflict.contains(arr.get(j))) {
                                    conflict.add(arr.get(j));
                                    condat.add(dat.get(j));
                                    contim.add(tim.get(j));
                                }
                            }
                            System.out.println("i, j: " + i + ", " + j);
                        }
                        if (!conflict.contains(arr.get(i)) && !cool.contains(arr.get(i))) {
                            cool.add(arr.get(i));
                            cooldat.add(dat.get(i));
                            cooltim.add(tim.get(i));
                        }
                        if (i == arr.size() - 2 && !conflict.contains(arr.get(i+1)) && !cool.contains(arr.get(i+1))) {
                            cool.add(arr.get(i+1));
                            cooldat.add(dat.get(i+1));
                            cooltim.add(tim.get(i+1));
                        }
                    }
                    System.out.println(cool);
                    System.out.println(conflict);
                    ObservableList<RSVPS> data = FXCollections.observableArrayList();
                    for (int i = 0; i < cool.size(); i++) {
                        data.addAll(new RSVPS(k, cool.get(i), cool.get(i), cooldat.get(i), cooltim.get(i)));
                        k++;
                    }
                    myRsvps.setItems(data);
                    ObservableList<RSVPS> data2 = FXCollections.observableArrayList();
                    for (int i = 0; i < conflict.size(); i++) {
                        data.addAll(new RSVPS(k, conflict.get(i), "CONFLICT: " + conflict.get(i), condat.get(i), contim.get(i)));
                        k++;
                    }
                    myRsvps.getItems().addAll(data2);
                } else {
                    ResultSet rs2 = HelloApplication.executeSelect("SELECT title FROM MyRsvps");
                    ResultSet rs3 = HelloApplication.executeSelect("SELECT date_featured FROM MyRsvps");
                    ResultSet rs4 = HelloApplication.executeSelect("SELECT time_featured FROM MyRsvps");
                    while (rs2.next()) {
                        myRsvps.getItems().addAll(new RSVPS(k, rs2.getString(1), rs2.getString(1), LocalDate.parse(rs3.getString(1)), LocalTime.parse(rs4.getString(1))));
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public class RSVPS {
        private ObservableValue<Integer> id;
        private Hyperlink evented;
        private LocalDate dated;
        private LocalTime timed;
        public RSVPS(int ID, String title, String e, LocalDate d, LocalTime t) {
            Hyperlink even = new Hyperlink();
            even.setText(String.valueOf(e));
            even.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        SeeEventsBasic.setCurrTitle(title);
                        ResultSet rs7 = HelloApplication.executeSelect("SELECT ID FROM Events WHERE title = '" + title + "';");
                        if(rs7.next()){
                            SeeEventsBasic.setCurrId(Integer.parseInt(rs7.getString(1)));
                        }
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewEventBasic.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Home");
                        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                        stage.setScene(scene2);
                        stage.show();
                        ((Node) e.getSource()).getScene().getWindow().hide();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println(currTitle);
                        System.out.println(Login.getCurrEmail());
                    }
                }
            });
            this.id = new SimpleObjectProperty<>(ID);
            this.evented = even;
            this.dated = d;
            this.timed = t;
        }
        public Hyperlink getE() {
            return evented;
        }
        public LocalDate getD() {
            return dated;
        }
        public LocalTime getT() {
            return timed;
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
    @FXML
    protected void handleBack(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationBasic.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
