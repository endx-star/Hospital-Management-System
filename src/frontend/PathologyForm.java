package FrontEnd;

import Controller.BillDAO;
import Controller.InpatientDAO;
import Controller.PathologyDAO;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Optional;


public class PathologyForm {
    private TextField pt_id;
    private TextField testName;
    private TextArea description;
    private TextField testId;
    private VBox vBox;
    GridPane gridPane;

    private Tooltip tooltip;
    private Tooltip tooltip1;
    private Tooltip tooltip2;
    private Tooltip tooltip3;
    private Tooltip tooltip4;
    private Tooltip tooltips;
    private Tooltip notFoundTooltip;
    private Tooltip isExist;

    private Tooltip[] toolTips;

    public Pane pathologyForm() {

        tooltip = Dr_form_pane.tooltip("Please insert Patient ID! ");/////
        tooltips = Dr_form_pane.tooltip("It should be only number. Please try again!");/////
        notFoundTooltip = Dr_form_pane.tooltip("Patient Not Registered with this ID");///////

        tooltip1 = Dr_form_pane.tooltip("It should be only number. Please try again!");
        isExist = Dr_form_pane.tooltip("This Test ID already exist. It Should be unique.");


        pt_id = new TextField();
        pt_id.setMaxWidth(170);


        testName = new TextField();
        testName.setMaxWidth(170);

        description = new TextArea();
        description.setWrapText(true);
        description.setMaxSize(270, 120);

        testId = new TextField();
        testId.setMaxWidth(170);

        HBox logout = new HBox(Menu_scene.logout());
        logout.setAlignment(Pos.TOP_RIGHT);

        Label label = Menu_scene.lblFormat("PATHOLOGY", 18);
        label.setUnderline(true);
        gridPane = new GridPane();

        gridPane.add(Menu_scene.lblFormat("Test ID", 13), 0, 0);
        gridPane.add(testId, 1, 0);
        testId.setOnKeyTyped(e -> {
            isExist.hide();
            InputValidator.keyTypedNumberHandlerM(tooltip1, testId, "process");
        });
        testId.setText(Integer.toString(InpatientDAO.lastIdGetter("pathology", "test_id")));

        gridPane.add(Menu_scene.lblFormat("Patient ID", 13), 0, 1);
        gridPane.add(pt_id, 1, 1);

        gridPane.add(Menu_scene.lblFormat("Test Name", 13), 0, 2);
        gridPane.add(testName, 1, 2);
        GridPane.setHalignment(Menu_scene.lblFormat("Test Name", 13), HPos.CENTER);

        gridPane.add(Menu_scene.lblFormat("Description", 13), 0, 3);
        gridPane.add(description, 1, 3);

        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        Button save = new Button("_Save");
        save.setOnAction(e -> savePathology(testId));
        Button btn = new Button("_See Detail");
        btn.setDisable(true);
       // Button btnEdit = new Button("_Edit");
        btn.setOnAction(e -> seeFullInformation());
        Button clear = new Button("_Clear");


        pt_id.setOnKeyTyped(e -> {
            tooltip.hide();
            if (!pt_id.getText().trim().isEmpty()) {
                if (notFoundTooltip.isShowing())
                    notFoundTooltip.hide();
                InputValidator.keyTypedNumberHandlerM(tooltips, pt_id, "process");//validate format
                if (!tooltips.isShowing()) {//  if you entered valid data
                    ////////Check is it registered or not
                    if (!InpatientDAO.idFounderChecker(Integer.parseInt(pt_id.getText()))) {
                        if (tooltips.isShowing())
                            tooltips.hide();
                        Bill_view.showTooltip(pt_id, notFoundTooltip);
                        btn.setDisable(true);

                    } else {
                        btn.setDisable(false);
                        notFoundTooltip.hide();
                        tooltips.hide();
                        tooltip.hide();
                    }
                }
            } else if (pt_id.getText().trim().isEmpty()) {
                btn.setDisable(true);
                notFoundTooltip.hide();
                tooltips.hide();
            }

        });


        HBox hBox = new HBox();
        hBox.getChildren().addAll(save, btn, clear);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        vBox = new VBox();
        vBox.getChildren().addAll(logout, label, gridPane, hBox);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(3, 40, 20, 20));
        return vBox;
    }

    public void savePathology(TextField test_id) {

        if (testId.getText().trim().isEmpty() || pt_id.getText().trim().isEmpty() || testName.getText().trim().isEmpty() || description.getText().isEmpty()) {
            Bill_view.message("Unable to save this information.\n You have Empty field");
            return;
        }

        if (!test_id.getText().trim().isEmpty()) {
            int testId = Integer.parseInt(test_id.getText().trim());
            if (!PathologyDAO.testIDChecker(testId)) {
                Bill_view.showTooltip(test_id, isExist);
                return;
            }
        }


        int id = Integer.parseInt(testId.getText());
        int pt_idd = Integer.parseInt(pt_id.getText());
        String desc = description.getText();
        String testNamen = testName.getText();
        PathologyDAO.insertPathology(id, pt_idd, testNamen, desc);
        clearTextfield();
    }

    public void clearTextfield() {
        pt_id.clear();
        description.clear();
        testId.clear();
        testName.clear();
    }

    public void seeFullInformation() {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeader(Menu_scene.lblFormat("See Detail Information", 15));

        dialogPane.setContent(fillInfo());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(dialogPane);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setY(350);
        dialog.setX(550);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CANCEL)
            System.out.println("Ok pressed");
    }

    public Pane fillInfo() {

        int pID = Integer.parseInt(pt_id.getText());

        TextField txtFName = new TextField();
        txtFName.setEditable(false);
        txtFName.setText(InpatientDAO.columnSelector("last_name", "inpatient", "id", pID));

        TextField txtLName = new TextField();
        txtLName.setEditable(false);
        txtLName.setText(InpatientDAO.columnSelector("first_name", "inpatient", "id", pID));

        TextField gender = new TextField();
        gender.setEditable(false);
        gender.setText(InpatientDAO.columnSelector("gender", "inpatient", "id", pID));

        TextField txtId = new TextField();
        txtId.setText(pt_id.getText());
        txtId.setEditable(false);

        TextField txtBirthDat = new TextField();
        txtBirthDat.setEditable(false);
        txtBirthDat.setText(InpatientDAO.columnSelector("birth_date", "inpatient", "id", pID));

        TextField txtAdmission = new TextField();
        txtAdmission.setEditable(false);
        txtAdmission.setText(InpatientDAO.columnSelector("admission_date", "inpatient", "id", pID));

        TextField txtAddress = new TextField();
        txtAddress.setEditable(false);
        txtAddress.setText(InpatientDAO.columnSelector("address", "inpatient", "id", pID));

        TextField txtContactNumber = new TextField();
        txtContactNumber.setEditable(false);
        txtContactNumber.setText(InpatientDAO.columnSelector("phone_no", "inpatient", "id", pID));


        //TextField txtFName = new TextField();
        // txtFName.setMaxWidth(150);
        txtFName.setText(InpatientDAO.columnSelector("first_name", "inpatient", "id", pID));

        //TextField txtLName = new TextField();
        txtLName.setText(InpatientDAO.columnSelector("last_name", "inpatient", "id", pID));

        //TextField gender = new TextField();
        gender.setText(InpatientDAO.columnSelector("gender", "inpatient", "id", pID));


        TextField testName = new TextField();

        TextArea description = new TextArea();
        description.setMaxSize(180, 130);
        description.setText(InpatientDAO.columnSelector("description", "pathology", "test_id", pID));
        // txtFName.setText(InpatientDAO.columnSelector("last_name", "inpatient", "id", pID));

        GridPane gPane = new GridPane();

        gPane.add(Menu_scene.lblFormat("First Name:", 12), 0, 0);
        gPane.add(txtFName, 1, 0);

        gPane.add(Menu_scene.lblFormat("Last Name:", 12), 0, 1);
        gPane.add(txtLName, 1, 1);

        gPane.add(Menu_scene.lblFormat("Patient\t ID:", 12), 0, 2);
        gPane.add(txtId, 1, 2);

        gPane.add(Menu_scene.lblFormat("Birth Date:", 12), 0, 3);
        gPane.add(txtBirthDat, 1, 3);

        gPane.add(Menu_scene.lblFormat("Gender:", 12), 0, 4);
        gPane.add(gender, 1, 4);

        gPane.add(Menu_scene.lblFormat("Address:", 12), 0, 5);
        gPane.add(txtAddress, 1, 5);

        // gPane.add(Menu_scene.lblFormat("Room", 12), 0, 6);
        //gPane.add(txtRoomNumber, 1, 6);

        gPane.add(Menu_scene.lblFormat("Phone No:", 12), 0, 7);
        gPane.add(txtContactNumber, 1, 7);

        gPane.add(Menu_scene.lblFormat("Date of \nAdmission:", 12), 0, 8);
        gPane.add(txtAdmission, 1, 8);

        gPane.add(Menu_scene.lblFormat("Test Name:", 12), 0, 9);
        gPane.add(testName, 1, 9);

        gPane.add(Menu_scene.lblFormat("Description:", 12), 0, 10);
        gPane.add(description, 1, 10);

        gPane.setVgap(8);

        gPane.setPadding(new Insets(15, 0, 0, 25));

        return gPane;
    }
}
