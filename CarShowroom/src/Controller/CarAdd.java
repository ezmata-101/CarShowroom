package Controller;

import Main.Car;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class CarAdd extends CarEditEdit {

    public JFXTextField reg_num_edit;
    public AnchorPane carAdd;

    public void onCancel(MouseEvent mouseEvent) {
        mainController.setMode(MainController.MANUFACTURER_VIEW_MODE);
    }

    public void onImageClick(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(carAdd.getScene().getWindow());
        handleImageChange(file);
    }

    public void onCreateCar(MouseEvent mouseEvent) {
        car = new Car(mainController);
        if(reg_num_edit.getText().equals("")){
            reg_num_edit.setText("");
            reg_num_edit.setStyle("-fx-prompt-text-fill: darkred;" +
                    "-fx-text-fill: darkred");
            reg_num_edit.setPromptText("* Can't be empty.");
            validData();
            return;
        }
        car.setRegistrationNumber(reg_num_edit.getText());
        if(setCar()) mainController.setMode(MainController.MANUFACTURER_VIEW_MODE);
    }
}
