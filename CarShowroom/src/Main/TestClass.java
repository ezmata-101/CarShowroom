package Main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestClass extends Application {
    ObservableList<AnchorPane> anchorPanes = FXCollections.observableArrayList();
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefWidth(200);
        pane.setPrefHeight(200);
        Car car = new Car();
        car.setRegistrationNumber("akas");
        ObservableList<Car> cars = FXCollections.observableArrayList();
        cars.add(car);
        ListView<Text> textListView = new ListView<Text>();
    }
}
