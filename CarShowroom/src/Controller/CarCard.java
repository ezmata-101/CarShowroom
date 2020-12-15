package Controller;

import Main.Car;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CarCard extends Controller{
    public ImageView carImage;
    public Text carMake, carModel, year, price;
    public Circle color1, color2, color3;
    private Car car;
    private MainController mainController;
    public void setCar(Car car, MainController mainController){
        this.mainController = mainController;
        this.car = car;
        carMake.setText(car.getMake());
        carModel.setText(car.getModel());
        year.setText("("+car.getYear()+")");
        price.setText(car.getPrice() + "$");
        String[] color = car.getColors();
        try{
            if(color[0] != null) color1.setFill(Color.valueOf(color[0]));
            if(color[1] != null) color2.setFill(Color.valueOf(color[1]));
            if(color[2] != null) color3.setFill(Color.valueOf(color[2]));
        }catch (Exception e){}
        try{
            carImage.setImage(car.getImage());
        }catch (Exception e){}
    }

    public void onSelect(MouseEvent mouseEvent) {
        mainController.setView(car);
    }
}
