package Main;

import Controller.CarCard;
import Controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

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
    public Car(){}
    public Car(String s){
        setFromString(s);
    }
    public Car(String registrationNumber, String make, String model, String[] colors, int year, int price, int quantity, String location) {
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
    }
    @Override
    public String toString() {
        String s = new String("");

        return "Car{" +
                "\n\t\tregistrationNumber='" + registrationNumber + '\'' +
                ", \n\t\tmake='" + make + '\'' +
                ", \n\t\tmodel='" + model + '\'' +
                ", \n\t\tcolors=" + Arrays.toString(colors) +
                ", \n\t\tyear=" + year +
                ", \n\t\tprice=" + price +
                ", \n\t\tquantity=" + quantity +
                ", \n\t\tlocation='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        try{
            Car c = (Car) obj;
            return c.registrationNumber.equalsIgnoreCase(this.registrationNumber);
        }catch (Exception e){
            return false;
        }
    }

    public static void main(String[] args) {
        Car c = new Car("abc123", "Toyota", "Corolla", new String[]{"WHITE", "BLACK"}, 2020, 2200000, 2, "null");
        System.out.println(c);
        System.out.println(c.getString());
        Car d = new Car();
        d.setFromString(c.getString());
        System.out.println(d.getString());
        System.out.println(d);
        Car e = new Car();
        e.setFromString(d.getString());
        System.out.println(e.getString());
        System.out.println(e);
    }
    private CarCard carCard;
    private AnchorPane carCardPane;
    public AnchorPane getCard(MainController m) {
        if(carCardPane == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/car_card.fxml"));
            try {
                carCardPane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.carCard = loader.getController();
        }
        this.carCard.setCar(this, m);
        return carCardPane;
    }

    public Image getImage() {
        if(!location.equalsIgnoreCase("null")){
            Image image = new Image("../Cache/"+location);
            return image;
        }
        return new Image("../Cache/default_car.png");
    }

    public String delete() {
        return "DELETE_CAR/"+string();
    }
}
