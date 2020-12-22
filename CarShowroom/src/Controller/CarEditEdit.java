package Controller;

import Main.Car;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CarEditEdit extends Controller implements Initializable {
    public AnchorPane carEditEdit;
    public JFXTextField year, left, make, model, price;

    public JFXColorPicker color1, color2, color3;
    public ImageView car_image;
    public Rectangle saveRec;
    public Text reg_num;
    public Rectangle cancelRec;
    protected Car car;

    FileChooser fileChooser;

    public void onColor1(ActionEvent actionEvent) { }

    public void onColor2(ActionEvent actionEvent) { }

    public void onColor3(ActionEvent actionEvent) {

    }

    public void onSaveButton(MouseEvent mouseEvent) {
        mainController.setMode(MainController.MANUFACTURER_VIEW_MODE);
        setCar();
    }
    protected boolean setCar(){
        if(!validData()) return false;
        try{
            car.setModel(model.getText());
            car.setMake(make.getText());
            car.setPrice(Integer.parseInt(price.getText()));
            car.setYear(Integer.parseInt(year.getText()));
            car.setQuantity(Integer.parseInt(left.getText()));
            String color = color1.getValue() + "," + color2.getValue() + "," + color3.getValue();
            car.setColorFromString(color);
            if(file != null){
                car.setLocation(file.getName());
            }
            mainController.setView(car);
            mainController.createOrEditCar(car.getString(), car.getRegistrationNumber());
            mainController.sendToServer(car.getString());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    protected boolean validData() {
        String car_model = model.getText();
        String car_make = make.getText();
        String car_price = price.getText();
        String car_year = year.getText();
        String car_quantity = left.getText();
        if(car_model.equals("")) cantBeEmpty(model);
        if(car_make.equals("")) cantBeEmpty(make);
        if(!isInteger(car_price)) shouldBeAnInteger(price);
        if(!isInteger(car_year)) shouldBeAnInteger(year);
        if(!isInteger(car_quantity)) shouldBeAnInteger(left);
        boolean flag = true;
        if(car_model.equals("")) flag = flag && false;
        if(car_make.equals("")) flag = flag && false;
        if(!isInteger(car_price)) flag = flag && false;
        if(!isInteger(car_year)) flag = flag && false;
        if(!isInteger(car_quantity)) flag = flag && false;
        return flag;
    }

    private boolean isInteger(String text){
        try{
            int i = Integer.parseInt(text);
            return i>=0;
        }catch (Exception e){
            return false;
        }
    }
    private void shouldBeAnInteger(JFXTextField textField){
        if(textField.getText().equals(""))cantBeEmpty(textField);
        else {
            textField.setText("");
            textField.setPromptText("* Should be an Integer.");
            textField.setStyle("-fx-text-fill: darkred;" +
                    "-fx-prompt-text-fill: darkred");
        }
    }
    private void cantBeEmpty(JFXTextField textField) {
        textField.setText("");
        textField.setPromptText("* Can't be empty...");
        textField.setStyle("-fx-text-fill: darkred;" +
                "-fx-prompt-text-fill: darkred");
    }
    private void clearText(JFXTextField textField, String promptText){
        textField.setText("");
        textField.setStyle("-fx-text-fill: darkred;" +
                "-fx-prompt-text-fill: darkred");
        textField.setPromptText(promptText);
    }
    private File file;
    public void onImageClick(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(carEditEdit.getScene().getWindow());
        handleImageChange(file);
    }

    protected void handleImageChange(File file){
        this.file = file;
        try{
            car_image.setImage(new Image("file:///"+file.getAbsolutePath()));
            mainController.sendToServer(file);
        }catch (Exception e){
            this.file = null;
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser = new FileChooser();
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
            if(color[0] != null) color1.setValue(Color.valueOf(color[0]));
            if(color[1] != null) color2.setValue(Color.valueOf(color[1]));
            if(color[2] != null) color3.setValue(Color.valueOf(color[2]));
        }catch (Exception e){}
        try{
            car_image.setImage(car.getImage());
        }catch (Exception e){
        }
    }

    public void onCancel(MouseEvent mouseEvent) {
        setCar(car);
        mainController.setMode(MainController.MANUFACTURER_VIEW_MODE);
    }
}
