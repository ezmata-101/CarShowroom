package Main;

import Controller.Controller;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    Stage stage;
    Controller currentController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Client client = new Client(this);
        changePane("car_edit", "Welcome Page", client);
    }
    public void changePane(String fxml, String stageName, Client client){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/"+fxml+".fxml"));
        stage.setTitle(stageName);
        try{
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load from FXML!");
        }
        currentController = loader.getController();
        currentController.setClient(client);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
