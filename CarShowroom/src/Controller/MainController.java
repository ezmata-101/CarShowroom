package Controller;

import Main.Car;
import Main.Client;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    public Pane detailPane;
    public JFXListView listView;
    public StackPane addButton;
    public Rectangle add_rec;
    private AnchorPane view, editView, editEdit, addView;
    private CarView carView;
    private CarEditView carEditView;
    private CarEditEdit carEditEdit;
    private CarAdd carAdd;
    private ObservableList<AnchorPane> observableList = FXCollections.observableArrayList();
    private List<Car> cars;
    public static final int VIEWER_MODE = 1;
    public static final int MANUFACTURER_VIEW_MODE = 2;
    public static final int MANUFACTURER_EDIT_MODE = 3;
    public static final int MANUFACTURER_ADD_MODE = 4;
    public static final int EMPTY = 5;
    private Car cCar;
    public void onSignOut(ActionEvent actionEvent) {
    }

    public void onClose(ActionEvent actionEvent) {
    }

    public void onSearchByReg(ActionEvent actionEvent) {
    }

    public void onSearchByName(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailPane.setVisible(false);
        listView.setItems(observableList);
        cars = new ArrayList<>();
//        client.setMainController(this);
//        setDetailViews();
    }
    public void setDetailViews(){
        setDetailViews(false);
    }
    public void setDetailViews(boolean manufacturer) {
        isManufacturer = manufacturer;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/car_view.fxml"));
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../FXMLS/car_edit_view.fxml"));
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../FXMLS/car_edit_edit.fxml"));
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("../FXMLS/car_add.fxml"));
        try {
            view = loader.load();
            editView = loader1.load();
            editEdit = loader2.load();
            addView = loader3.load();
            detailPane.getChildren().addAll(view, editView, editEdit, addView);
            carView = loader.getController();
            carEditView = loader1.getController();
            carEditEdit = loader2.getController();
            carAdd = loader3.getController();
            setClientAndMainController(carView);
            setClientAndMainController(carEditView);
            setClientAndMainController(carEditEdit);
            setClientAndMainController(carAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setClientAndMainController(Controller controller){
        controller.setClient(Client.getInstance());
        controller.setMainController(this);
    }

    public void createOrEditCar(String message, String reg) {
        Car c = getCarForReg(reg);
        if(c == null) cars.add(new Car(message));
        else c.setFromString(message);
        updateCars();
    }
    private Car getCarForReg(String reg){
        for(Car c: cars){
            if(c.getRegistrationNumber().equalsIgnoreCase(reg)) return c;
        }
        return null;
    }
    public void deleteCar(String reg) {
        if(cCar.getRegistrationNumber().equalsIgnoreCase(reg)) setView(new Car());
        Car c = getCarForReg(reg);
        if(c != null){
            cars.remove(c);
            updateCars();
        }
    }
    public void updateCars(){
        observableList.clear();
        for(Car c: cars){
            observableList.add(c.getCard(this));
            if(cCar != null && c.getRegistrationNumber().equalsIgnoreCase(cCar.getRegistrationNumber())) setView(c);
        }
        listView.setItems(observableList);
    }

    public void setView(Car car) {
//        return;
        cCar = car;
        carView.setCar(car);
        carEditEdit.setCar(car);
        carEditView.setCar(car);
    }

    public void onCarSelection(MouseEvent mouseEvent) {
        detailPane.setVisible(true);
        if(isManufacturer) setMode(MANUFACTURER_VIEW_MODE);
        else setMode(VIEWER_MODE);
    }

    public void sendToServer(String message) {
        client.sendMessage(message);
    }
    public void setMode(int mode){
        view.setVisible(false);
        editView.setVisible(false);
        editEdit.setVisible(false);
        addView.setVisible(false);
        if(mode == VIEWER_MODE) view.setVisible(true);
        else if(mode == MANUFACTURER_VIEW_MODE) editView.setVisible(true);
        else if(mode == MANUFACTURER_EDIT_MODE) editEdit.setVisible(true);
        else if(mode == MANUFACTURER_ADD_MODE) addView.setVisible(true);
    }

    public void setIsManufacturer(boolean isManufacturer) {
        addButton.setVisible(isManufacturer);
        this.isManufacturer = isManufacturer;
        if(isManufacturer) setMode(MANUFACTURER_VIEW_MODE);
        else setMode(VIEWER_MODE);
    }

    public void onAddButton(MouseEvent mouseEvent) {
        detailPane.setVisible(true);
        setMode(MANUFACTURER_ADD_MODE);
    }
}
