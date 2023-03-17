package FrontEnd;

import Controller.Login_controller;
import DataModel.Login_model;
import DataModel.Patient;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class UserList {
    private static TableView<Login_model> login_modelTableView = new TableView<>();

    public static TableView<Login_model> login_modelTableView() {


        login_modelTableView.setPrefHeight(Integer.MAX_VALUE);
        login_modelTableView.setPlaceholder(Menu_scene.formattingLabel("Empty List", Color.rgb(255, 0, 128), 25));

        TableColumn<Login_model, String> user_name = new TableColumn<>("User Name");
        user_name.setPrefWidth(130);
        user_name.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

        TableColumn<Login_model, String> password = new TableColumn<>("Password");
        password.setPrefWidth(200);
        password.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        TableColumn<Login_model, String> roll = new TableColumn<>("Roll");
        roll.setPrefWidth(200);
        roll.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        login_modelTableView.getColumns().clear();
        login_modelTableView.getColumns().addAll(user_name, password, roll);
        return login_modelTableView;

    }

    public static void populateUsers() throws SQLException {
       // login_modelTableView.setItems(Login_controller.getUserInfo());
    }

}
