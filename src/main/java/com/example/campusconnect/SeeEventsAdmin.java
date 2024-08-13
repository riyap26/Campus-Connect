package com.example.campusconnect;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SeeEventsAdmin implements Initializable {

    public static String currTitle;

    public static String getCurrTitle() {
        return currTitle;
    }

    public static void setCurrTitle(String s) {
        currTitle = s;
    }

    public static int countId;
    public static int getCountId() {
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



    private final static int dataSize = 100;

    @FXML private Pagination pagination;
    @FXML private final TableView<Event> myAdminEvents = createTable();
    @FXML private final List<Event> data = createData();
    @FXML private DatePicker sDate;
    @FXML private ComboBox<String> loc2;
    @FXML private TextField host;
    private final static int rowsPerPage = 10;

    private TableView<Event> createTable() {

        TableView<Event> table = new TableView<>();

        TableColumn<Event, Hyperlink> even = new TableColumn<>("Event");
        even.setCellValueFactory(param -> new ObservableHyperLink(param.getValue().even));
        even.setPrefWidth(100);

        TableColumn<Event, String> datt = new TableColumn<>("Date");
        datt.setCellValueFactory(param -> param.getValue().dated);
        datt.setPrefWidth(70);

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
                    data.add(new SeeEventsAdmin.Event(i, rs.getString(1), rs2.getString(1)));
                    i++;
                }
                return data;
            } else {
                ResultSet rs = HelloApplication.executeSelect("SELECT Title FROM BrowseRSVPs" + countId);
                ResultSet rs2 = HelloApplication.executeSelect("SELECT date_featured FROM BrowseRSVPs" + countId);
                int i = 1;
                while (rs.next() && rs2.next()) {
                    data.add(new SeeEventsAdmin.Event(i, rs.getString(1), rs2.getString(1)));
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
        myAdminEvents.setItems(FXCollections.observableList(data.subList(fromIndex, toIndex)));
        return myAdminEvents;
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
                        sLoc = null;
                        sHost = "";
                        seDate = null;
                        countId = 0;
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewEventAdmin.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Home");
                            Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                            stage.setScene(scene2);
                            stage.show();
                            ((Node) e.getSource()).getScene().getWindow().hide();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println(currTitle);
                        System.out.println(Login.getCurrEmail());
                    }
                }
            });
            this.id = new SimpleObjectProperty<>(ID);
            this.even = even;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.dated = new SimpleStringProperty(date);
        }

        public Hyperlink getEven() {
            return even;
        }

        public SimpleStringProperty getDate() {
            return dated;
        }
    }

    @FXML void filterEvents(MouseEvent event) {
        try {
            myAdminEvents.getItems().clear();

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
                myAdminEvents.getItems().addAll(new SeeEventsAdmin.Event(Integer.parseInt(rs3.getString(1)), rs.getString(1), rs2.getString(1)));
            }
            countId = count;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    protected void handleClear(MouseEvent event) throws IOException {
        loc2.setValue(null);
        host.setText("");
        sDate.setValue(null);
        if (myAdminEvents != null) {
            try {
                myAdminEvents.getItems().clear();
                ResultSet rs = HelloApplication.executeSelect("SELECT title FROM Events");
                ResultSet rs2 = HelloApplication.executeSelect("SELECT date_featured FROM Events");
                ResultSet rs3 = HelloApplication.executeSelect("SELECT ID FROM Events");

                while (rs.next() && rs2.next() && rs3.next()) {
                    myAdminEvents.getItems().addAll(new SeeEventsAdmin.Event(Integer.parseInt(rs3.getString(1)), rs.getString(1), rs2.getString(1)));
                }
                countId = 0;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    protected void handleMap(MouseEvent event) throws IOException {
        sLoc = loc2.getValue();
        sHost = host.getText();
        seDate = sDate.getValue();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mapAdmin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Map");
        Scene scene2 = new Scene(fxmlLoader.load(), 800, 570);
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
