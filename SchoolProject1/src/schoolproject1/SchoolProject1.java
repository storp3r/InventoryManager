package SchoolProject1;



import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import static javafx.application.Application.launch;

import javafx.scene.Scene;




public class SchoolProject1 extends Application {

    public AnchorPane mainMenu;

    public FXMLLoader fxmlloader;

    private Stage activeWindow;

 

    @Override public void start(Stage stage) throws Exception {

        activeWindow = stage;

        activeWindow.setTitle("Inventory Management System");

        fxmlloader = new FXMLLoader();

        fxmlloader.setLocation(SchoolProject1.class.getResource("/View_Controller/MainMenu.fxml"));

        mainMenu = (AnchorPane) fxmlloader.load();

        Scene scene = new Scene(mainMenu);

        activeWindow.setScene(scene);

        activeWindow.setResizable(false);

        activeWindow.show();

    }

   

    public static void main(String[] args) {

        launch(args);

    }

}