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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MapBasic implements Initializable {
    @FXML
    private DatePicker sDate;
    @FXML private ComboBox<String> loc2;
    @FXML private TextField host;
    @FXML private Text curr;
    @FXML private Hyperlink CRC1;
    @FXML private Hyperlink CRC2;
    @FXML private Hyperlink CRC3;
    @FXML private Hyperlink CRC4;
    @FXML private Hyperlink STAMPS1;
    @FXML private Hyperlink STAMPS2;
    @FXML private Hyperlink ISYE1;
    @FXML private Hyperlink ISYE2;
    @FXML private Hyperlink ISYE3;
    @FXML private Hyperlink EXHIBITION1;
    @FXML private Hyperlink EXHIBITION2;
    @FXML private Hyperlink FERST1;
    @FXML private Hyperlink FERST2;
    @FXML private Hyperlink FERST3;
    @FXML private Hyperlink FLAG1;
    @FXML private Hyperlink FLAG2;
    @FXML private Hyperlink STUDENT1;
    @FXML private Hyperlink STUDENT2;
    @FXML private Hyperlink STUDENT3;
    @FXML private Hyperlink COC1;
    @FXML private Hyperlink COC2;
    @FXML private Hyperlink VANLEER1;
    @FXML private Hyperlink VANLEER2;
    @FXML private Hyperlink TECHGREEN1;
    @FXML private Hyperlink TECHGREEN2;
    @FXML private Hyperlink TECHGREEN3;
    @FXML private Hyperlink TECHGREEN4;
    @FXML private Hyperlink KLAUS1;
    @FXML private Hyperlink KLAUS2;
    @FXML private Hyperlink KLAUS3;
    @FXML private Hyperlink ARCH1;
    @FXML private Hyperlink ARCH2;
    @FXML private Hyperlink CULC1;
    @FXML private Hyperlink CULC2;
    @FXML private Hyperlink PRICE1;
    @FXML private Hyperlink PRICE2;
    @FXML private Hyperlink CROSSLAND1;
    @FXML private Hyperlink SKILES1;
    @FXML private Hyperlink SKILES2;
    @FXML private Hyperlink TECHTOWER1;
    @FXML private Hyperlink BILLMOORE1;

    protected HashMap<String, String> icons = new HashMap<>();
    protected HashMap<String, String> matchLocs = new HashMap<>();
    protected ArrayList<Hyperlink> hyps = new ArrayList<>();



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (loc2 != null) {
            loc2.setItems(CreateEvent.locs);
            loc2.setValue(SeeEventsBasic.getsLoc());
        }
        if (host != null) {
            host.setText(SeeEventsBasic.getsHost());
        }
        if (sDate != null) {
            sDate.setValue(SeeEventsBasic.getseDate());
        }
        initList();
        initList2();
        Collections.addAll(hyps, CRC1, CRC2, CRC3, CRC4, STAMPS1, STAMPS2, ISYE1, ISYE2, ISYE3, EXHIBITION1,
                EXHIBITION2, FERST1, FERST2, FERST3, FLAG1, FLAG2, STUDENT1, STUDENT2, STUDENT3, COC1, COC2, VANLEER1,
                VANLEER2, TECHGREEN1, TECHGREEN2, TECHGREEN3, TECHGREEN4, KLAUS1, KLAUS2, KLAUS3, ARCH1, ARCH2, CULC1,
                CULC2, PRICE1, PRICE2, CROSSLAND1, SKILES1, SKILES2, TECHTOWER1, BILLMOORE1);
        if (SeeEventsBasic.countId == 0) {
            try {
                ResultSet rs = HelloApplication.executeSelect("SELECT title FROM Events ORDER BY date_featured");
                //ResultSet rs2 = HelloApplication.executeSelect("SELECT ID FROM Events ORDER BY date_featured");
                ResultSet rs3 = HelloApplication.executeSelect("SELECT location FROM Events ORDER BY date_featured");
                String s = "";
                while(rs.next() && rs3.next()) {
                   if (matchLocs.containsKey(rs3.getString(1))) {
                       s = matchLocs.get(rs3.getString(1));
                   }
                   for (int i = 1; i < 5; i++) {
                       if (icons.containsKey(s + i)) {
                          if(icons.get(s+i) == null) {
                              icons.put(s+i, rs.getString(1));
                              i = 5;
                          }
                       }
                   }
                }
                for (Hyperlink elem : hyps) {
                    System.out.println(elem.getId());
                    if (icons.containsKey(elem.getId())) {
                        if (icons.get(elem.getId()) != null) {
                            elem.setVisible(true);
                        } else {
                            elem.setVisible(false);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                ResultSet rs = HelloApplication.executeSelect("SELECT title FROM BrowseRSVPs" + SeeEventsBasic.countId + " ORDER BY date_featured");
                //ResultSet rs2 = HelloApplication.executeSelect("SELECT ID FROM Events ORDER BY date_featured");
                ResultSet rs3 = HelloApplication.executeSelect("SELECT location FROM BrowseRSVPs" + SeeEventsBasic.countId + " ORDER BY date_featured");
                String s = "";
                while(rs.next()  && rs3.next()) {
                    if (matchLocs.containsKey(rs3.getString(1))) {
                        s = matchLocs.get(rs3.getString(1));
                    }
                    for (int i = 1; i < 5; i++) {
                        if (icons.containsKey(s + i)) {
                            if(icons.get(s+i) == null) {
                                icons.put(s+i, rs.getString(1));
                                i = 5;
                            }
                        }
                    }
                }
                for (Hyperlink elem : hyps) {
                    System.out.println(elem.getId());
                    if (icons.containsKey(elem.getId())) {
                        if (icons.get(elem.getId()) != null) {
                            elem.setVisible(true);
                        } else {
                            elem.setVisible(false);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @FXML
    protected void handleOver(MouseEvent event) throws IOException {
        Hyperlink hyp = (Hyperlink) event.getSource();
        String id = hyp.getId();
        if (icons.get(id) != null) {
            SeeEventsBasic.setCurrTitle(icons.get(id));
            curr.setText(icons.get(id));
        }
    }

    @FXML
    protected void handleOut(MouseEvent event) throws IOException {
        curr.setText("None");
    }

    @FXML
    protected void handleBack(MouseEvent event) throws IOException {
        SeeEventsBasic.setsLoc(loc2.getValue());
        SeeEventsBasic.setsHost(host.getText());
        SeeEventsBasic.setseDate(sDate.getValue());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationBasic.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Home");
        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML void filterEvents(MouseEvent event) {
        try {
            //myBasicEvents.getItems().clear();

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
            SeeEventsBasic.setCountId(count);
            ResultSet rs = HelloApplication.executeSelect("SELECT title FROM BrowseRSVPs" + count + " ORDER BY date_featured");
            //ResultSet rs2 = HelloApplication.executeSelect("SELECT date_featured FROM BrowseRSVPs" + count);
            ResultSet rs3 = HelloApplication.executeSelect("SELECT location FROM BrowseRSVPs" + count + " ORDER BY date_featured");
            String s = "";
            initList();
            initList2();
            while(rs.next()  && rs3.next()) {
                if (matchLocs.containsKey(rs3.getString(1))) {
                    s = matchLocs.get(rs3.getString(1));
                }
                for (int i = 1; i < 5; i++) {
                    if (icons.containsKey(s + i)) {
                        if(icons.get(s+i) == null) {
                            icons.put(s+i, rs.getString(1));
                            i = 5;
                        }
                    }
                }
            }
            System.out.println(icons);
            for (Hyperlink elem : hyps) {
                System.out.println(elem.getId());
                if (icons.containsKey(elem.getId())) {
                    if (icons.get(elem.getId()) != null) {
                        elem.setVisible(true);
                    } else {
                        elem.setVisible(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    public void handleView(MouseEvent e) {
        try {
            Hyperlink hyp = (Hyperlink) e.getSource();
            String id = hyp.getId();
            if (icons.get(id) != null) {
                SeeEventsBasic.setCurrTitle(icons.get(id));
                curr.setText(icons.get(id));
            }
            ResultSet rs = HelloApplication.executeSelect("SELECT ID FROM Events WHERE hostEmail= '"
                    + Login.getCurrEmail() + "' AND title = '" + SeeEventsBasic.currTitle + "';");
            ResultSet rs2 = HelloApplication.executeSelect("SELECT ID FROM Events WHERE title = '" + SeeEventsBasic.currTitle + "';");
            if (rs2.next()) {
                SeeEventsBasic.setCurrId(Integer.parseInt(rs2.getString(1)));
            }
            if (rs.next()) {
                SeeEventsBasic.setCurrId(Integer.parseInt(rs.getString(1)));
                SeeEventsBasic.setsLoc(null);
                SeeEventsBasic.setsHost("");
                SeeEventsBasic.setseDate(null);
                SeeEventsBasic.setCountId(0);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("myviewBasic.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Home");
                Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene2);
                stage.show();
                ((Node) e.getSource()).getScene().getWindow().hide();
            } else {
                SeeEventsBasic.setsLoc(null);
                SeeEventsBasic.setsHost("");
                SeeEventsBasic.setseDate(null);
                SeeEventsBasic.setCountId(0);
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
            System.out.println(SeeEventsBasic.currTitle);
            System.out.println(Login.getCurrEmail());
        }
    }

    @FXML
    protected void handleClear(MouseEvent event) throws IOException {
        loc2.setValue(null);
        host.setText("");
        sDate.setValue(null);
        SeeEventsBasic.setCountId(0);
        initList();
            try {
                ResultSet rs = HelloApplication.executeSelect("SELECT title FROM Events ORDER BY date_featured");
                //ResultSet rs2 = HelloApplication.executeSelect("SELECT ID FROM Events ORDER BY date_featured");
                ResultSet rs3 = HelloApplication.executeSelect("SELECT location FROM Events ORDER BY date_featured");
                String s = "";
                while(rs.next() && rs3.next()) {
                    if (matchLocs.containsKey(rs3.getString(1))) {
                        s = matchLocs.get(rs3.getString(1));
                    }
                    for (int i = 1; i < 5; i++) {
                        if (icons.containsKey(s + i)) {
                            if(icons.get(s+i) == null) {
                                icons.put(s+i, rs.getString(1));
                                i = 5;
                            }
                        }
                    }
                }
                for (Hyperlink elem : hyps) {
                    System.out.println(elem.getId());
                    if (icons.containsKey(elem.getId())) {
                        if (icons.get(elem.getId()) != null) {
                            elem.setVisible(true);
                        } else {
                            elem.setVisible(false);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    protected void initList() {
        icons.put("CRC1", null);
        icons.put("CRC2", null);
        icons.put("CRC3", null);
        icons.put("CRC4", null);
        icons.put("STAMPS1", null);
        icons.put("STAMPS2", null);
        icons.put("ISYE1", null);
        icons.put("ISYE2", null);
        icons.put("ISYE3", null);
        icons.put("EXHIBITION1", null);
        icons.put("EXHIBITION2", null);
        icons.put("FERST1", null);
        icons.put("FERST2", null);
        icons.put("FERST3", null);
        icons.put("FLAG1", null);
        icons.put("FLAG2", null);
        icons.put("STUDENT1", null);
        icons.put("STUDENT2", null);
        icons.put("STUDENT3", null);
        icons.put("COC1", null);
        icons.put("COC2", null);
        icons.put("VANLEER1", null);
        icons.put("VANLEER2", null);
        icons.put("TECHGREEN1", null);
        icons.put("TECHGREEN2", null);
        icons.put("TECHGREEN3", null);
        icons.put("TECHGREEN4", null);
        icons.put("KLAUS1", null);
        icons.put("KLAUS2", null);
        icons.put("KLAUS3", null);
        icons.put("ARCH1", null);
        icons.put("ARCH2", null);
        icons.put("CULC1", null);
        icons.put("CULC2", null);
        icons.put("PRICE1", null);
        icons.put("PRICE2", null);
        icons.put("CROSSLAND1", null);
        icons.put("SKILES1", null);
        icons.put("SKILES2", null);
        icons.put("TECHTOWER1", null);
        icons.put("BILLMOORE1", null);
    }

    protected void initList2() {
        matchLocs.put("Campus Recreation Center", "CRC");
        matchLocs.put("Stamps", "STAMPS");
        matchLocs.put("ISyE Building", "ISYE");
        matchLocs.put("Exhibition Hall", "EXHIBITION");
        matchLocs.put("Ferst Center","FERST");
        matchLocs.put("Flag Building", "FLAG");
        matchLocs.put("Student Center","STUDENT");
        matchLocs.put("College of Computing", "COC");
        matchLocs.put("Van Leer", "VANLEER");
        matchLocs.put("Tech Green", "TECHGREEN");
        matchLocs.put("Klaus", "KLAUS");
        matchLocs.put("Architecture", "ARCH");
        matchLocs.put("Clough Library", "CULC");
        matchLocs.put("Price Gilbert", "PRICE");
        matchLocs.put("Crossland Tower", "CROSSLAND");
        matchLocs.put("Skiles", "SKILES");
        matchLocs.put("Tech Tower", "TECHTOWER");
        matchLocs.put("Bill Moore", "BILLMOORE");
    }
}
