package Controller;

import Main.Car;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.MouseEvent;

public class CarAdd extends CarEditEdit{

    public JFXTextField reg_num_edit;

    public void onCancel(MouseEvent mouseEvent) {
    }

    public void onCreateCar(MouseEvent mouseEvent) {
        car = new Car(mainController);
        car.setRegistrationNumber(reg_num_edit.getText());
        if(setCar()) mainController.setMode(MainController.MANUFACTURER_VIEW_MODE);
    }
}
