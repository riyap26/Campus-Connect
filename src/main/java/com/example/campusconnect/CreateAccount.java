package com.example.campusconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class CreateAccount {
    public static String currEmail;
    public static int userType = 1;
    public Connection con = HelloApplication.con;

    @FXML private TextField emailLogin;
    @FXML private TextField nameLogin;
    @FXML private TextField passwordLogin;
    @FXML private TextField passwordConfirm;
    @FXML private RadioButton basic;
    @FXML private RadioButton admin;
    @FXML private RadioButton executive;


    public static String getCurrEmail() {
        return currEmail;
    }

    public static void setCurrEmail(String currEmail) {
        CreateAccount.currEmail = currEmail;
    }

    public static int getUserType() {
        return userType;
    }

    public static void setUserType(int userType) {
        CreateAccount.userType = userType;
    }

    @FXML
    public void initialize() {
        final ToggleGroup group = new ToggleGroup();
        admin.setToggleGroup(group);
        basic.setToggleGroup(group);
        executive.setToggleGroup(group);
    }

    @FXML
    protected void handleLogin(MouseEvent event) throws IOException {
        try {
            if (admin.isSelected()) {
                setUserType(2);
            }
            else if (basic.isSelected()) {
                setUserType(1);
            }
            if (nameLogin.getText() != null && emailLogin.getText() != null && passwordLogin.getText().length() > 5
                    && passwordLogin.getText().equals(passwordConfirm.getText())) {
                HelloApplication.executeInsert("INSERT INTO Users Values ( '" + emailLogin.getText()
                        + "', '" + nameLogin.getText() + "', '" + passwordLogin.getText() + "', '" + getUserType() + "');");
                currEmail = emailLogin.getText();
                System.out.println(getUserType());
                    if (getUserType() == 1) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationBasic.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Home");
                        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                        stage.setScene(scene2);
                        stage.show();
                        ((Node) event.getSource()).getScene().getWindow().hide();
                    }
                    if (getUserType() == 2) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paginationAdmin.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Home");
                        Scene scene2 = new Scene(fxmlLoader.load(), 600, 400);
                        stage.setScene(scene2);
                        stage.show();
                        ((Node) event.getSource()).getScene().getWindow().hide();
                    }
                }

        } catch(Exception e){
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
