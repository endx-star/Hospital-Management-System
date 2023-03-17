package FrontEnd;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

/**
 *
 * @web http://zoranpavlovic.blogspot.com/
 */
public class Main extends Application {
    // private Button login;
    private boolean response1 = true;
    private boolean response2 = true;
    public Button login;
    private Label userName;
    private Label pass;
    private TextField uname;
    private PasswordField password;
    String user = "JavaFX2";
    String pw = "password";
    String checkUser, checkPw;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to major hospitals");

        BorderPane bp = new BorderPane();
       // BackgroundImage bi = new BackgroundImage(new Image("3.jpg", 400, 400, false, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        bp.setPadding(new Insets(10, 50, 50, 50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(100, 20, 100, 30));
        hb.setId("img");
        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        CheckBox c1 = new CheckBox();
        Label lbldoc = new Label("Doctor");
        CheckBox c2 = new CheckBox();
        Label lbllab = new Label("Labratories");
        CheckBox c3 = new CheckBox();
        Label lblcard = new Label("Cardroom");
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password");
        final PasswordField pf = new PasswordField();
        Button login = new Button("Login");
        final Label lblMessage = new Label();
        HBox bb1 = new HBox();
        bb1.setAlignment(Pos.BOTTOM_LEFT);
        bb1.getChildren().addAll(c1, lbldoc, c2, lbllab, c3, lblcard);
        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(login, 2, 1);
        gridPane.add(lblMessage, 1, 2);
        gridPane.add(bb1, 1, 2);
        gridPane.setHalignment(bb1, HPos.LEFT);

        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("Major hospital");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        login.setId("btnLogin");
        text.setId("text");

        //Action for btnLogin


        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp, 500, 300);
        //bp.setBackground(new Background(bi));
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e ->
        {
            e.consume();
          //  AlertBox.closeProgram(primaryStage);
        });
        primaryStage.show();
    }

}