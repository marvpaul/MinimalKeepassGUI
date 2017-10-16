import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.FXWindow;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main extends Application {
    FXWindow window;

    //Start point :)
    public static void main(String[] args) throws Exception{

        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
        Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("icon3.png"));
        Image icon = new Image("./icon3.png");
        primaryStage.getIcons().add(icon);
        window = new FXWindow(primaryStage);



    }


}
