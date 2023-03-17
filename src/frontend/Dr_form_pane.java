package FrontEnd;

import Controller.DoctorDAO;
import Controller.InpatientDAO;
import DataModel.Doctor;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Dr_form_pane {
    private BorderPane pane = new BorderPane();
    private GridPane gPane = new GridPane();
    private doctor_table_model doctor_table_model1;
    private TextField txtFName = new TextField();
    private TextField txtLName = new TextField();
    private ComboBox<String> txtGender;
    private TextField txtId = new TextField();
    private DatePicker txtBirthDat = new DatePicker();
    private TextField txtAddress = new TextField();
    private TextField txtPhoneNumber = new TextField();
    private TextField txtQualification = new TextField();

    private TextField[] txt = {txtFName, txtLName, txtId, txtAddress, txtPhoneNumber, txtQualification};
    private Tooltip[] tooltips = {new Tooltip(), new Tooltip(), new Tooltip(), new Tooltip(), new Tooltip(), new Tooltip()};


    public Dr_form_pane() {

        gPane.setHgap(12);
        gPane.setVgap(10);
        doctor_table_model1 = new doctor_table_model();
    }

    public Pane addDoctor(int mode) { Label lblFName = FrontEnd.Pt_form_pane.lbl_format("First _Name:");
        gPane.add(lblFName, 0, 0);

        txtFName.widthProperty().addListener(ov -> txtFName.setPrefWidth(15 + Form_Window.stageProvider().getWidth() - 450));
        gPane.add(txtFName, 1, 0);

        Label lblLName = FrontEnd.Pt_form_pane.lbl_format("Last _Name:");
        gPane.add(lblLName, 0, 1);
        gPane.add(txtLName, 1, 1);

        Label lblGender = FrontEnd.Pt_form_pane.lbl_format("_Gender:");
        gPane.add(lblGender, 0, 2);

        txtGender = new ComboBox<>();
        txtGender.getItems().addAll("Male ", "Female\t\t\t\t ", "Both");

        txtGender.getSelectionModel().selectFirst();
        gPane.add(txtGender, 1, 2);

        Label lblPhone = FrontEnd.Pt_form_pane.lbl_format("Phone N_o:");
        gPane.add(lblPhone, 0, 3);
        gPane.add(txtPhoneNumber, 1, 3);

        Label lblBirth = FrontEnd.Pt_form_pane.lbl_format("Birth _Date:");
        txtBirthDat.setValue(LocalDate.now());
        gPane.add(lblBirth, 0, 4);
        gPane.add(txtBirthDat, 1, 4);

        Label lblId = FrontEnd.Pt_form_pane.lbl_format("I_D:");
        gPane.add(lblId, 0, 5);
        txtId.setText("12");
        gPane.add(txtId, 1, 5);
        txtId.setEditable(false);
        txtId.setText(Integer.toString(InpatientDAO.lastIdGetter("staff", "dr_id")));

        Label lblAd = FrontEnd.Pt_form_pane.lbl_format("_Address:");
        gPane.add(lblAd, 0, 6);
        gPane.add(txtAddress, 1, 6);

        Label lblQual = FrontEnd.Pt_form_pane.lbl_format("_Qualification:");
        gPane.add(lblQual, 0, 7);
        gPane.add(txtQualification, 1, 7);
        gPane.setPadding(new Insets(15, 0, 0, 25));


        Button btnAdd = new Button("_Save");
        btnAdd.setDefaultButton(true);
        Button btnCancel = new Button("Cancel");
        Button btnClear = new Button("Clear");
        btnCancel.setCancelButton(true);


        Label label;
        if (mode == 4) {
            label = new Label("Staff's Data Editing Form");// fill the Whole fields.
            label.setFont(Font.font("Rockwell", FontWeight.BOLD, FontPosture.ITALIC, 20));
            label.setTextFill(Color.CORNFLOWERBLUE);
            label.setUnderline(true);

            txtId.setText(Integer.toString(doctor_table_model.getId()));
            txtAddress.setText(doctor_table_model.getAddress());
            txtLName.setText(doctor_table_model.getLastName());
            txtFName.setText(doctor_table_model.getFirstName());
            txtPhoneNumber.setText(doctor_table_model.getPhoneNumber());
            txtGender.getSelectionModel().select(doctor_table_model.getGender());
            txtQualification.setText(doctor_table_model.getQualification());

            // txtBirthDat.setValue(Date.valueOf(InpatientTableModel.getBirthDate()).toLocalDate());
            // txtBirthDat.setValue(Date.valueOf(doctor_table_model.getAdmissionDate()).toLocalDate());
            btnAdd.setOnAction(e -> {
                try {
                    handle_addingDoctor_information(4);
                    FrontEnd.Form_Window.closeWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        /////gPane should be border

        else {
            label = new Label("Staff Registration Form");// fill the Whole fields.
            label.setFont(Font.font("Rockwell", FontWeight.BOLD, FontPosture.ITALIC, 20));
            label.setTextFill(Color.CORNFLOWERBLUE);
            label.setUnderline(true);
            btnAdd.setOnAction(e -> {
                try {
                    handle_addingDoctor_information(0);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }


        HBox textBox = new HBox();
        textBox.getChildren().addAll(label);
        textBox.setAlignment(Pos.CENTER);



        btnCancel.setOnAction(e -> FrontEnd.Form_Window.closeWindow());
        btnClear.setOnAction(e -> clear_txt_field());

        HBox btnBox = new HBox();
        btnBox.setSpacing(15);
        btnBox.getChildren().addAll(btnClear, btnAdd, btnCancel);
        btnBox.setAlignment(Pos.BOTTOM_RIGHT);

        pane.setTop(textBox);
        pane.setCenter(gPane);
        pane.setBottom(btnBox);
        pane.setPadding(new Insets(5, 5, 5, 5));

        //////////////////////validate input///
        String action_type = "process";
        tooltips[0] = Dr_form_pane.tooltip("Invalid name. Please try again!");
        txtFName.setOnKeyTyped(e -> InputValidator.keyTypedLetterHandler(tooltips[0], txtFName, action_type));

        tooltips[1] = Dr_form_pane.tooltip("Invalid name. Please try again!");
        txtLName.setOnKeyTyped(e -> InputValidator.keyTypedLetterHandler(tooltips[1], txtLName, action_type));

        // tooltips[2] = Dr_form_pane.tooltip("Room No should be only digit");
        //txtRoomNumb.setOnKeyTyped(e -> InputValidator.keyTypedNumberHandler(tooltips[2], txtRoomNumber, action_type));

        tooltips[3] = Dr_form_pane.tooltip("Invalid Phone Number!");
        txtPhoneNumber.setOnKeyTyped(e -> InputValidator.keyTypedPhoneHandler(tooltips[3], txtPhoneNumber, action_type));


        return pane;

    }

    private void handle_addingDoctor_information(int state) throws SQLException {
        if (!InputValidator.isInvalid(tooltips)) {
            AlertBox.message("\t👉Unable to add this information!\n You have invalid field !" +
                    "\n\t\tPlease try again!");
        } else if (!InputValidator.isEmpty(txt, gPane)) {
            AlertBox.message("You have empty field.");
        } else save_doctors(state);
    }


    public void clear_txt_field() {
        txtFName.clear();
        txtLName.clear();
        txtAddress.clear();
        //txtId.clear();
        txtPhoneNumber.clear();
        System.out.println(txtBirthDat.getValue());
        txtQualification.clear();
        for (int i = 0; i < tooltips.length; i++) {
            if (gPane.getChildren().contains(tooltips[i])) {
                gPane.getChildren().remove(tooltips[i]);
            }
        }
    }


    public void save_doctors(int state) throws SQLException {
        String firstName = txtFName.getText();
        String lastName = txtLName.getText();
        String phone = txtPhoneNumber.getText();
        int id = Integer.parseInt(txtId.getText());
        String gender = txtGender.getSelectionModel().getSelectedItem();
        String address = txtAddress.getText();
        java.sql.Date age = Date.valueOf(txtBirthDat.getValue());
        String qualification = txtQualification.getText();
        DoctorDAO.insertStaffInfo(id, firstName, lastName, gender, age, address, phone, qualification, state);//add to database
        doctor_table_model1.showStaffInfo();
        clear_txt_field();
        txtId.setText(Integer.toString(InpatientDAO.lastIdGetter("staff", "dr_id")));

    }

    public static Tooltip tooltip(String message) {
        Tooltip tipInformation = new Tooltip();
        tipInformation.setText(message);
        tipInformation.setMaxWidth(280);
        tipInformation.setWrapText(true);
        tipInformation.setShowDelay(Duration.ZERO);
        tipInformation.setAutoHide(false);
        tipInformation.setStyle("-fx-text-fill:red;" +
                "-fx-background-color:white");
        tipInformation.setFont(Font.font("Gloucester MT", FontWeight.BOLD, FontPosture.ITALIC, 12));

        return tipInformation;
    }

    public static void hideTooltip(Tooltip tooltip) {
        tooltip.hide();
    }

    public static void showTooltip(TextField pF, Tooltip tooltip) {
        Point2D p = pF.localToScene(pF.getBoundsInLocal().getMaxX(), pF.getBoundsInLocal().getMaxY());
        tooltip.show(pF,
                p.getX() + Form_Window.stageProvider().getScene().getX() + Form_Window.stageProvider().getX() + 5,
                p.getY() + Form_Window.stageProvider().getScene().getY() + Form_Window.stageProvider().getY() - 15);
    }
}

