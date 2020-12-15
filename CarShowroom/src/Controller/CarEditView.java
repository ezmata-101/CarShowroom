package Controller;

import Main.Car;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CarEditView extends Controller implements Initializable {

    public AnchorPane anchorPane;
    public Text reg_num, make, model, year, price, left;
    public Rectangle color1;
    public Rectangle color2;
    public Rectangle color3;
    public ImageView car_image;
    private Car car;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onEdit(MouseEvent mouseEvent) {
        mainController.viewEdit(true);
    }

    public void onDelete(MouseEvent mouseEvent) {
        mainController.sendToServer(car.delete());
    }

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
