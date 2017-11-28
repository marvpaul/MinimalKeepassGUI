package view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DatabaseModel;
import org.linguafranca.pwdb.Entry;
import org.linguafranca.pwdb.kdbx.simple.SimpleEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FXWindow {
    public ListView<String> listView;
    private DatabaseModel databaseModel;
    public TextArea tA;
    public TextField tf;
    private Stage primStage;
    private Button goDirUp;

    public FXWindow(Stage primaryStage) {
        primStage = primaryStage;
        //Initialize TextArea for showing password and username
        tA = new TextArea();
        tA.editableProperty().setValue(false);
        tA.setMaxHeight(100);
        tA.getStyleClass().add("card-title");

        //Initialize TextField for searching some entries
        tf = new TextField();
        tf.getStyleClass().add("control-label");

        goDirUp = new Button("Up");
        HBox menuBar = new HBox();
        menuBar.getChildren().add(goDirUp);

        listView = new ListView<>();

        setEvents();

        //Initialize the database
        databaseModel = new DatabaseModel(this);

        //Add a layout and all components to the layout
        BorderPane root = new BorderPane();
        root.setCenter(listView);
        root.setBottom(tA);
        root.setTop(menuBar);

        Scene scene = new Scene(root, 500, 500);

        //apply stylesheet
        scene.getStylesheets().add("css/material-fx-v0_3.css");

        primaryStage.setTitle("Minimal Keepass GUI");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Set event handlers for searching and selecting entries
     */
    private void setEvents(){
        tf.setOnKeyReleased(event -> {
            databaseModel.searchInDatabase(tf.getText());
            setList(databaseModel.stringList);
        });

        listView.setOnMouseClicked(event -> {
            Integer entry = listView.getSelectionModel().getSelectedIndices().get(0);
            databaseModel.open(entry);
        });
        goDirUp.setOnMouseClicked(event -> {
            databaseModel.goUp();
        });
    }

    /**
     * Set the list for list view with entries from database
     * @param list a list with user entries
     */
    public void setList(List<String> list){
        listView.setItems(FXCollections.observableArrayList (list));
    }

    public void showEntry(SimpleEntry entry){
        tA.setText(entry.getUsername() + " : " + entry.getPassword());
    }

    /**
     * Opens a dialogue and ask user for file path
     * @return String with path to keepass file
     */
    public String askForPath(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Database");
        File selectedFile = fileChooser.showOpenDialog(primStage);

        return selectedFile.getAbsolutePath();
    }

    /**
     * Opens a dialogue where the user is asked for password
     */
    public void askForPassword(){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Your password");
        Button accept = new Button("Let's decrypt!");
        VBox vbox = new VBox();
        vbox.getChildren().add(passwordField);
        vbox.getChildren().add(accept);

        Scene scene = new Scene(vbox, 150, 100);
        Stage secondStage = new Stage();
        secondStage.setScene(scene);
        secondStage.setAlwaysOnTop(true);
        secondStage.show();

        accept.setOnAction(event -> {
            databaseModel.applyPass(passwordField.getText());
            secondStage.close();
        });

        passwordField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                databaseModel.applyPass(passwordField.getText());
                secondStage.close();
            }
        });

    }

}
