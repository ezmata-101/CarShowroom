package Controller;

import javafx.scene.input.MouseEvent;

public class CarEditView extends CarController{
    public void onEdit(MouseEvent mouseEvent) {
        mainController.setMode(MainController.MANUFACTURER_EDIT_MODE);
    }
    public void onDelete(MouseEvent mouseEvent) {
        mainController.setMode(MainController.EMPTY);
        mainController.sendToServer(car.delete());
    }
}
