package Main;

import Controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage stage;
    private Controller currentController;
    private Client client;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        client = Client.getInstance(this);
        changePane("login", "Welcome Page", client);
    }
    public void changePane(String fxml, String stageName, Client client){
        System.out.println("Asked to change!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/"+fxml+".fxml"));
        System.out.println(loader);
        if(stage != null)stage.close();
        stage = new Stage();
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(stageName);
        AnchorPane pane = null;
        try{
            pane = loader.load();
            stage.setResizable(true);
            stage.setScene(new Scene(pane));
            stage.setOnCloseRequest(e -> close());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load from FXML!");
        }
        System.out.println(pane);
        currentController = loader.getController();
        currentController.setClient(client);
        if(fxml.equalsIgnoreCase("main"))client.setMainController(currentController);
        stage.show();
    }

    private void close() {
        client.disconnect();
    }

    public void setNotification(String notification){
        currentController.setNotification(notification);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
