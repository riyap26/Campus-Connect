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

public class SeeEventsBasic implements Initializable {

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

    @FXML private Pagination pagination;
    @FXML private final TableView<Event> myBasicEvents = createTable();
    @FXML private final List<Event> data = createData();
    @FXML private DatePicker sDate;
    @FXML private ComboBox<String> loc2;
    @FXML private TextField host;
    private final static int rowsPerPage = 10;

    private TableView<Event> createTable() {

        TableView<Event> table = new TableView<>();

        TableColumn<Event, Hyperlink> even = new TableColumn<>("Event");
        even.setCellValueFactory(param -> new ObservableHyperLink(param.getValue().even));
        even.setPrefWidth(150);

        TableColumn<Event, String> datt = new TableColumn<>("Date");
        datt.setCellValueFactory(param -> param.getValue().dated);
        datt.setPrefWidth(200);

        table.getColumns().addAll(even, datt);
        return table;
    }

    @FXML
    private List<Event> createData() {
        List<Event> data = new ArrayList<>(dataSize);
        try {
            if (countId == 0) {
                ResultSet rs = HelloApplication.executeSelect("SELECT Title FROM Events");
                ResultSet rs2 = HelloApplication.executeSelect("SELECT date_featured FROM Events");
                int i = 1;
                while (rs.next() && rs2.next()) {
                    data.add(new Event(i, rs.getString(1), rs2.getString(1)));
                    i++;
                }
                return data;
            } else {
                ResultSet rs = HelloApplication.executeSelect("SELECT Title FROM BrowseRSVPs" + countId);
                ResultSet rs2 = HelloApplication.executeSelect("SELECT date_featured FROM BrowseRSVPs" + countId);
                int i = 1;
                while (rs.next() && rs2.next()) {
                    data.add(new Event(i, rs.getString(1), rs2.getString(1)));
                    i++;
                }
                return data;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pagination.setPageFactory(this::createPage);

        if (loc2 != null) {
            loc2.setValue(sLoc);
            loc2.setItems(CreateEvent.locs);
        }
        if (host != null) {
            host.setText(sHost);
        }
        if (sDate != null) {
            sDate.setValue(seDate);
        }


    }


    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        myBasicEvents.setItems(FXCollections.observableList(data.subList(fromIndex, toIndex)));
        return myBasicEvents;
    }

    public class Event {
        private final ObservableValue<Integer> id;
        private final Hyperlink even;
        private final SimpleStringProperty dated;

        public Event(int ID, String eve, String date) {
            Hyperlink even = new Hyperlink();
            even.setText(String.valueOf(eve));
            even.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        currTitle = even.getText();
                        ResultSet rs = HelloApplication.executeSelect("SELECT ID FROM Events WHERE hostEmail= '"
                                + Login.getCurrEmail() + "' AND title = '" + currTitle + "';");
                        ResultSet rs2 = HelloApplication.executeSelect("SELECT ID FROM Events WHERE title = '" + currTitle + "';");
                        if (rs2.next()){
                            currID = Integer.parseInt(rs2.getString(1));
                        }
                        if (rs.next()) {
                            currID = Integer.parseInt(rs.getString(1));
                            sLoc = null;
                            sHost = "";
                            seDate = null;
                            countId = 0;
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("myviewBasic.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Home");
                            Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                            stage.setScene(scene2);
                            stage.show();
                            ((Node) e.getSource()).getScene().getWindow().hide();
                        } else {
                            sLoc = null;
                            sHost = "";
                            seDate = null;
                            countId = 0;
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewEventBasic.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Home");
                            Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                            stage.setScene(scene2);
                            stage.show();
                            ((Node) e.getSource()).getScene().getWindow().hide();
                        }
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                        System.out.println(currTitle);
                        System.out.println(Login.getCurrEmail());
                    }
                }
            });
            this.id = new SimpleObjectProperty<>(ID);
            this.even = even;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.dated = new SimpleStringProperty(date);
        }

        public Hyperlink getEven() {
            return even;
        }

        public SimpleStringProperty getDate() {
            return dated;
        }
    }

    @FXML
    protected void handleClear(MouseEvent event) throws IOException {
        loc2.setValue(null);
        host.setText("");
        sDate.setValue(null);
        if (myBasicEvents != null) {
            try {
                myBasicEvents.getItems().clear();
                ResultSet rs = HelloApplication.executeSelect("SELECT title FROM Events");
                ResultSet rs2 = HelloApplication.executeSelect("SELECT date_featured FROM Events");
                ResultSet rs3 = HelloApplication.executeSelect("SELECT ID FROM Events");

                while (rs.next() && rs2.next() && rs3.next()) {
                    myBasicEvents.getItems().addAll(new Event(Integer.parseInt(rs3.getString(1)), rs.getString(1), rs2.getString(1)));
                }
                countId = 0;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    protected void handleCreateBasic(MouseEvent event) throws IOException {
        sLoc = null;
        sHost = "";
        seDate = null;
        countId = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createEvent.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();

    }
    @FXML
    protected void handleSeeMyRSVPS(MouseEvent event) throws IOException {
        sLoc = null;
        sHost = "";
        seDate = null;
        countId = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("seeMyRsvps.fxml"));
        Stage stage = new Stage();
        stage.setTitle("SeeAll");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    @FXML void filterEvents(MouseEvent event) {
        try {
            myBasicEvents.getItems().clear();

            String hostQuery = host.getText();
            LocalDate dQuery = sDate.getValue();
            String locQuery = loc2.getValue();
            int count = 2;

            HelloApplication.executeInsert("DROP VIEW IF EXISTS BrowseRSVPs1;");
            HelloApplication.executeInsert("CREATE VIEW BrowseRSVPs1 (ID, title, location, date_featured, host) " +
                    " AS SELECT ID, title, location, date_featured, name FROM Events e INNER JOIN Users u ON e.hostEmail = u.email;");

            if (hostQuery != null && !hostQuery.equals("")) {
                HelloApplication.executeInsert("DROP VIEW IF EXISTS BrowseRSVPs" + count + ";");
                HelloApplication.executeInsert("CREATE VIEW BrowseRSVPs" + count + " (ID, title, location, date_featured, host) AS SELECT * FROM BrowseRSVPs" + (count - 1) + " WHERE host = '" + hostQuery + "';");
                count++;
            }
            if (locQuery != null && !locQuery.equals("")) {
                HelloApplication.executeInsert("DROP VIEW IF EXISTS BrowseRSVPs" + count + ";");
                HelloApplication.executeInsert("CREATE VIEW BrowseRSVPs" + count + " (ID, title, location, date_featured, host) AS SELECT * FROM BrowseRSVPs" + (count - 1) + " WHERE location = '" + locQuery + "';");
                count++;
            }
            if (dQuery != null && !dQuery.equals("")) {
                HelloApplication.executeInsert("DROP VIEW IF EXISTS BrowseRSVPs" + count + ";");
                HelloApplication.executeInsert("CREATE VIEW BrowseRSVPs" + count + " (ID, title, location, date_featured, host) AS SELECT * FROM BrowseRSVPs" + (count - 1) + " WHERE date_featured = '" + dQuery + "';");
                count++;
            }

            count--;
            ResultSet rs = HelloApplication.executeSelect("SELECT title FROM BrowseRSVPs" + count);
            ResultSet rs2 = HelloApplication.executeSelect("SELECT date_featured FROM BrowseRSVPs" + count);
            ResultSet rs3 = HelloApplication.executeSelect("SELECT ID FROM BrowseRSVPs" + count);

            while (rs.next() && rs2.next() && rs3.next()) {
                myBasicEvents.getItems().addAll(new Event(Integer.parseInt(rs3.getString(1)), rs.getString(1), rs2.getString(1)));
            }
            countId = count;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    protected void handleLogoff(MouseEvent event) throws IOException {
        sLoc = null;
        sHost = "";
        seDate = null;
        countId = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    protected void handleMap(MouseEvent event) throws IOException {
        sLoc = loc2.getValue();
        sHost = host.getText();
        seDate = sDate.getValue();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mapBasic.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Map");
        Scene scene2 = new Scene(fxmlLoader.load(), 800, 570);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
