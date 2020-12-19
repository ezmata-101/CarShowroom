package Main;

import Controller.CarCard;
import Controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Car {
    private String registrationNumber;
    private String make;
    private String model;
    private String colors[];
    private int year;
    private int price;
    private int quantity;
    private String location;
    public Car(MainController m){this.mainController = m;}
    public Car(String s, MainController m) {
        this(m);
        setFromString(s);
    }
    public Car(MainController m, String registrationNumber, String make, String model, String[] colors, int year, int price, int quantity, String location) {
        this(m);
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.colors = colors;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
        this.location = location;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColorString() {
        String c = "";
        for(int i = 0; i< Math.min(3, colors.length); i++){
            if(colors[i] != null) c = c+ colors[i]+",";
        }
        return c;
    }
    public void setColorFromString(String colors){
        this.colors = new String[3];
        try{
            String ss[] = colors.split(",");
            for(int i = 0; i< Math.min(ss.length, 3); i++) this.colors[i] = ss[i];
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private String string(){
        return registrationNumber + "/" +
                make + "/" +
                model + "/" +
                getColorString() + "/" +
                year + "/" +
                price + "/" +
                quantity + "/" +
                location;
    }
    public String getString(){
        String s = "CAR_OBJECT"+"/"+
                string();
        return s;
    }
    public void setFromString(String s){
        String ss[] = s.split("/");
        setRegistrationNumber(ss[1]);
        setMake(ss[2]);
        setModel(ss[3]);
        setColorFromString(ss[4]);
        setYear(Integer.parseInt(ss[5]));
        setPrice(Integer.parseInt(ss[6]));
        setQuantity(Integer.parseInt(ss[7]));
        setLocation(ss[8]);
        carCardPane = null;
        image = null;
        setImage();
    }

    private CarCard carCard;
    private AnchorPane carCardPane;
    private MainController mainController;
    public AnchorPane getCard() {
        if(carCardPane == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/car_card.fxml"));
            try {
                carCardPane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.carCard = loader.getController();
            this.carCard.setCar(this, mainController);
        }
        return carCardPane;
    }
    private Image image;
    public Image getImage() {
        if(image == null) setImage();
        return image;
    }
    private void setImage(){
        image = new Image("Cache/default_car.png");
        if(location.equals("null")) return;
        try{
            File file = new File("src/CarImages/"+location);
            if(!file.exists()){
                mainController.sendToServer("request:carImage/"+location);
                return;
            }
            image = new Image("file:///"+file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String delete() {
        return "DELETE_CAR/"+string();
    }

    public void buy() {
        quantity -= 1;
    }
}
