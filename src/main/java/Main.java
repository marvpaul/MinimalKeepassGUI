import javafx.application.Application;
import javafx.stage.Stage;
import view.FXWindow;

public class Main extends Application {
    FXWindow window;

    //Start point :)
    public static void main(String[] args) throws Exception{
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
        window = new FXWindow(primaryStage);



    }


}
