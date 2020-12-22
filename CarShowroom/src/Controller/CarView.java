package Controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CarView extends CarController {

    public void onBuy(MouseEvent mouseEvent) {
        if(car.getQuantity() > 0){
            car.buy();
            setCar(car);
            mainController.sendToServer(car.getString());
            left.setFill(Color.BLACK);
        }else{
            left.setText("OUT OF STOCK");
            left.setFill(Color.DARKRED);
        }
    }
}
