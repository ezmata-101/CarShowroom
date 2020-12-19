package Controller;

import Main.Car;
import Main.Client;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    public Pane detailPane;
    public JFXListView listView;
    public Rectangle add_rec;
    public JFXTextField make_name, model_name, reg_num;
    public Pane searchByMakePane, searchByRegPane;
    private AnchorPane view, editView, editEdit, addView, addCarCard;
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
    private final int ALL_CAR_MODE = 1;
    private final int CAR_WITH_REG = 2;
    private final int CAR_WITH_MAKE_N_MODEL = 3;
    private int viewMode;
    private Car cCar;

    private String searchRegNo, searchMake, searchModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailPane.setVisible(false);
        searchByMakePane.setVisible(false);
        searchByRegPane.setVisible(false);
        listView.setItems(observableList);
        cars = new ArrayList<>();
        viewMode = ALL_CAR_MODE;
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
        if(isManufacturer) observableList.add(getAddCarCard());
        if(viewMode == ALL_CAR_MODE){
            for(Car c: cars){
                observableList.add(c.getCard(this));
                if(cCar != null && c.getRegistrationNumber().equalsIgnoreCase(cCar.getRegistrationNumber())) setView(c);
            }
        }
        if(viewMode == CAR_WITH_REG){
            for(Car c:cars){
                if(c.getRegistrationNumber().equalsIgnoreCase(searchRegNo)) {
                    observableList.add(c.getCard(this));
                    setView(c);
                    break;
                }
            }
        }
        if(viewMode == CAR_WITH_MAKE_N_MODEL){
            for(Car c: cars){
                if(searchMake.equalsIgnoreCase(c.getMake())){
                    if(searchModel.equalsIgnoreCase(searchModel) || searchModel.equals("")){
                        observableList.add(c.getCard(this));
                        if(cCar != null && cCar.getRegistrationNumber().equalsIgnoreCase(c.getRegistrationNumber()))setView(c);
                    }
                }
            }
        }
        listView.setItems(observableList);
    }
    private AnchorPane getAddCarCard() {
        if (addCarCard == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/car_add_card.fxml"));
            try {
                addCarCard = loader.load();
                Controller controller = loader.getController();
                controller.setMainController(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addCarCard;
    }

    public void setView(Car car) {
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
    public void sendToServer(File file){
        client.sendFile(file);
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
        this.isManufacturer = isManufacturer;
        if(isManufacturer) setMode(MANUFACTURER_VIEW_MODE);
        else setMode(VIEWER_MODE);
    }

    public void onAddButton(MouseEvent mouseEvent) {
        detailPane.setVisible(true);
        setMode(MANUFACTURER_ADD_MODE);
    }

    public void showSearchByReg(ActionEvent actionEvent) {
        searchByRegPane.setVisible(true);
        searchByMakePane.setVisible(false);
    }

    public void showSearchByMakeNModel(ActionEvent actionEvent) {
        searchByRegPane.setVisible(false);
        searchByMakePane.setVisible(true);
    }

    public void showAllCars(ActionEvent actionEvent) {
        searchByRegPane.setVisible(false);
        searchByMakePane.setVisible(false);
        viewMode = ALL_CAR_MODE;
        searchRegNo = null;
        searchMake = null;
        searchModel = null;
        updateCars();
    }

    public void onSearchByMakeAndModel(ActionEvent actionEvent) {
        if(make_name.getText().equals("") && model_name.getText().equals("")) return;
        viewMode = CAR_WITH_MAKE_N_MODEL;
        searchRegNo = null;
        searchMake = make_name.getText();
        searchModel = model_name.getText();
        if(searchModel.equalsIgnoreCase("any")) searchModel = "";
        searchByMakePane.setVisible(false);
        updateCars();
    }
    public void onSearchByReg(ActionEvent actionEvent) {
        if(reg_num.getText().equals("")) return;
        viewMode = CAR_WITH_REG;
        searchMake = null;
        searchModel = null;
        searchRegNo = reg_num.getText();
        searchByRegPane.setVisible(false);
        updateCars();
    }

    public void onSignOut(ActionEvent actionEvent) {
    }

    public void onClose(ActionEvent actionEvent) {
    }
}
