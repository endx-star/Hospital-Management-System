package FrontEnd;

import Controller.DoctorDAO;
import DataModel.Doctor;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

public class doctor_table_model {
    private static TableView<Doctor> table = new TableView<>();
    public static int id;

    private static String firstName;
    private static String lastName;
    private static Date birthDate;
    private static String gender;
    private static String address;
    private static String phoneNumber;
    private static String qualification;

    public static void setId(int id) {
        doctor_table_model.id = id;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        doctor_table_model.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        doctor_table_model.lastName = lastName;
    }

    public static Date getBirthDate() {
        return birthDate;
    }

    public static void setBirthDate(Date birthDate) {
        doctor_table_model.birthDate = birthDate;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        doctor_table_model.gender = gender;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        doctor_table_model.address = address;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        doctor_table_model.phoneNumber = phoneNumber;
    }

    public static String getQualification() {
        return qualification;
    }

    public static void setQualification(String qualification) {
        doctor_table_model.qualification = qualification;
    }

    public doctor_table_model() {
    }


    public TableView<Doctor> table() {
        ////////////////column name///////////////////
        TableColumn<Doctor, Integer> dr_ID = new TableColumn<>("ID");
        dr_ID.setPrefWidth(95);
        dr_ID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Doctor, String> dr_firstName = new TableColumn<>("First Name");
        dr_firstName.setPrefWidth(200);
        dr_firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());

        TableColumn<Doctor, String> dr_lastName = new TableColumn<>("Last Name");
        dr_lastName.setPrefWidth(200);
        dr_lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        TableColumn<Doctor, String> dr_gender = new TableColumn<>("Gender");
        dr_gender.setPrefWidth(90);
        dr_gender.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        TableColumn<Doctor, Date> dr_birthDate = new TableColumn<>("Birth Date");
        dr_birthDate.setPrefWidth(150);
        dr_birthDate.setCellValueFactory(cellData -> cellData.getValue().ageProperty());

        TableColumn<Doctor, String> dr_address = new TableColumn<>("Address");
        dr_address.setPrefWidth(200);
        dr_address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        TableColumn<Doctor, String> dr_phoneNumber = new TableColumn<>("Phone Number");
        dr_phoneNumber.setPrefWidth(150);
        dr_phoneNumber.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        TableColumn<Doctor, String> dr_qualification = new TableColumn<>("Qualification");
        dr_qualification.setPrefWidth(200);
        dr_qualification.setCellValueFactory(cellData -> cellData.getValue().qualificationProperty());

        table.setEditable(true);
        table.setLayoutX(190);
        table.setLayoutY(102);
        table.setTableMenuButtonVisible(true);
        table.setPrefHeight(600);
        table.setPrefWidth(600);
        table.setPlaceholder(Menu_scene.formattingLabel("Empty List", Color.rgb(255, 0, 128), 40));
        table.getColumns().clear();

        table.getColumns().addAll(dr_ID, dr_firstName, dr_lastName, dr_gender, dr_birthDate, dr_address,
                dr_phoneNumber, dr_qualification);

        table.setContextMenu(context_menu());/////add,edit,delete,sort By,refresh
        table.setOnMouseClicked(e -> getId());

        return table;
    }

    public static int getId() {
        Doctor doctor = table.getSelectionModel().getSelectedItem();
        if (doctor == null) {
            return 0;
        }
        id = doctor.getId();

        setFirstName(doctor.getFirstName());
        setLastName(doctor.getLastName());
        setAddress(doctor.getAddress());
        setPhoneNumber(doctor.getPhone());
        setGender(doctor.getGender());
        setQualification(doctor.getQualification());

        return id;
    }

    public static void deleteSelectedItem() throws SQLException {
        if (id == 0) {
            AlertBox.selectionChecker("Please select the record  that you want to delete", "No entry selected");
            return;
        } else {
            deleteConfirmationAlert("Are you sure you want to delete with id=" + id);

        }
    }

    public static void deleteConfirmationAlert(String text) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(text);
        alert.setHeaderText(null);

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");

        alert.getButtonTypes().addAll(yes, no);
        alert.getButtonTypes().removeAll(ButtonType.OK);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yes) {
            DoctorDAO.deleteSelectedRecorred(id);
            showStaffInfo();
            id = 0;
        }
        if (result.isPresent() && result.get() == no) {
            id = 0;
        }
    }

    public static void showStaffInfo() throws SQLException {
        table.setItems(DoctorDAO.getStaffList());
    }

    private ContextMenu context_menu() {
        DrScene drScene=new DrScene();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem add_menu = new MenuItem("_Add New Patient");
        add_menu.setOnAction(e->drScene.doctor_form_panel(3));
        add_menu.setAccelerator(KeyCombination.keyCombination("Shortcut+A"));

        MenuItem edit_menu = new MenuItem("_Edit Selected Row");
        edit_menu.setOnAction(e->drScene.doctor_form_panel(4));
        edit_menu.setAccelerator(KeyCombination.keyCombination("Shortcut+E"));

        MenuItem delete_menu = new MenuItem("_Delete Selected Row");
        delete_menu.setOnAction(e-> {
            try {
                deleteSelectedItem();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        delete_menu.setAccelerator(KeyCombination.keyCombination("Shortcut+D"));

        MenuItem refresh = new MenuItem("_Refresh");

        Menu sort_menu = new Menu("_Sort By");
        ToggleGroup toggles = new ToggleGroup();
        RadioMenuItem name_menu = new RadioMenuItem("_Name");
        name_menu.setToggleGroup(toggles);
        RadioMenuItem id_menu = new RadioMenuItem("Patient _ID");
        id_menu.setToggleGroup(toggles);
        RadioMenuItem date_menu = new RadioMenuItem("_Date Modified");
        date_menu.setToggleGroup(toggles);
        sort_menu.getItems().addAll(name_menu, id_menu, date_menu);
        id_menu.setSelected(true);
        contextMenu.getItems().addAll(add_menu, edit_menu, delete_menu, sort_menu, refresh);
        return contextMenu;
    }

    public static void showSpecificStaff(int id, String name) throws SQLException {
        table.setItems(DoctorDAO.getSpecificSatffInfo(id, name));
    }
}
