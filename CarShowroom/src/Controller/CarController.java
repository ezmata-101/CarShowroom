package Controller;

import Main.Car;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CarController extends Controller{
    public AnchorPane anchorPane;
    public Text reg_num, make, model, year, price, left;
    public ImageView car_image;
    public Rectangle color1, color2, color3;

    protected Car car;
    public void setCar(Car car) {
        this.car = car;
        reg_num.setText(car.getRegistrationNumber());
        make.setText(car.getMake());
        model.setText(car.getModel());
        year.setText(String.valueOf(car.getYear()));
        price.setText(String.valueOf(car.getPrice()));
        left.setText(String.valueOf(car.getQuantity()));
        String[] color = car.getColors();
        try{
            if(color[0] != null) color1.setFill(Color.valueOf(color[0]));
            if(color[1] != null) color2.setFill(Color.valueOf(color[1]));
            if(color[2] != null) color3.setFill(Color.valueOf(color[2]));
        }catch (Exception e){}
        try{
            car_image.setImage(car.getImage());
        }catch (Exception e){
        }
    }
}
