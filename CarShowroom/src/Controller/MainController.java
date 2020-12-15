package Controller;

import Main.Car;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    public Pane detailPane;
    public JFXListView listView;
    private AnchorPane view, editView, editEdit;
    private CarView carView;
    private CarEditView carEditView;
    private CarEditEdit carEditEdit;
    private ObservableList<AnchorPane> observableList = FXCollections.observableArrayList();
    private List<Car> cars;
    public void onSignOut(ActionEvent actionEvent) {
    }

    public void onClose(ActionEvent actionEvent) {
    }

    public void onSearchByReg(ActionEvent actionEvent) {
    }

    public void onSearchByName(ActionEvent actionEvent) {
    }
    
    public void addCar(String s){
        cars.add(new Car(s));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailPane.setVisible(false);
        listView.setItems(observableList);
        cars = new ArrayList<>();
//        client.setMainController(this);
        setDetailViews();
    }
    public void setDetailViews(){
        setDetailViews(false);
    }
    public void setDetailViews(boolean manufacturer) {
        isManufacturer = manufacturer;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/car_view.fxml"));
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../FXMLS/car_edit_view.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../FXMLS/car_edit_edit.fxml"));
        try {
            view = loader.load();
            editView = loader1.load();
            editEdit = loader2.load();

            carView = loader.getController();
            carEditView = loader1.getController();
            carEditEdit = loader2.getController();

            detailPane.getChildren().addAll(view, editEdit, editView);
            carEditView.setMainController(this);
            carEditEdit.setMainController(this);
            carView.setMainController(this);

            carEditView.setClient(client);
            carEditEdit.setClient(client);
            carView.setClient(client);

            view.setVisible(false);
            editView.setVisible(false);
            editEdit.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createOrEditCar(String message, String reg) {
        boolean flag = false;
        for(Car c:cars){
            if(c.getRegistrationNumber().equalsIgnoreCase(reg)){
                c.setFromString(message);
                flag = true;
                break;
            }
        }
        if(!flag){
            Car car = new Car(message);
            cars.add(car);
            if(car.getCard(this) != null)observableList.add(car.getCard(this));
        }
        updateCars();
    }
    public void updateCars(){
        int index = listView.getSelectionModel().getSelectedIndex();
        observableList.clear();
        for(Car c: cars){
            observableList.add(c.getCard(this));
        }
        listView.setItems(observableList);
        listView.getSelectionModel().select(index);
    }
    public void setView(Car car) {
//        return;
        editView.setVisible(isManufacturer);
        view.setVisible(!isManufacturer);
        carView.setCar(car);
        carEditEdit.setCar(car);
        carEditView.setCar(car);
    }

    public void onCarSelection(MouseEvent mouseEvent) {
        System.out.println(client == null);
        detailPane.setVisible(true);
    }

    public void viewEdit(boolean flag) {
        System.out.println(flag);
        editEdit.setVisible(flag);
        editView.setVisible(!flag);
    }

    public void sendToServer(String message) {
        client.sendMessage(message);
    }

    public void deleteCar(String reg) {
        for(Car c: cars){
            if(c.getRegistrationNumber().equalsIgnoreCase(reg)){
                cars.remove(c);
                updateCars();
                return;
            }
        }
    }
}
