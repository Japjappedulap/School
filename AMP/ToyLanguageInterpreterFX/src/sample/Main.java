package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("runForm.fxml"));
        Parent mainWindow = mainLoader.load();

        RunFormController mainWindowController = mainLoader.getController();

        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(mainWindow, 620, 600));
        primaryStage.show();


        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("selectForm.fxml"));
        Parent secondaryWindow = secondaryLoader.load();

        SelectFormController selectWindowController = secondaryLoader.getController();
        selectWindowController.setMainWindowController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select Window");
        secondaryStage.setScene(new Scene(secondaryWindow, 500, 550));
        secondaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
