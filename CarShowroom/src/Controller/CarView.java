package Controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CarView extends CarController {

    public void onBuy(MouseEvent mouseEvent) {
        System.out.println("On Buy");
        if(car.getQuantity() > 0){
            car.buy();
            setCar(car);
            mainController.sendToServer(car.getString());
        }else{
            System.out.println("Red hoy na");
            left.setText("OUT OF STOCK");
            left.setFill(Color.DARKRED);
        }
    }
}
