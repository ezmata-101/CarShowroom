package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.Socket;
import java.util.List;

public class Main extends Application {

    Point2D points[] = new Point2D[15];

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Pane root = new Pane();
        root.setPrefSize(700, 700);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        FileChooser fileChooser = new FileChooser();
        List<File> file = fileChooser.showOpenMultipleDialog(primaryStage);
        Socket socket = new Socket("localhost", 11111);
        FileTransfer fileTransfer = new FileTransfer(socket);
        fileTransfer.addToFile(file);
        fileTransfer.startSending();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
