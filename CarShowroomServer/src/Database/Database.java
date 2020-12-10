package Database;

import Classes.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements Constants{
    Connection connection;
    private Database(){}
    public boolean open(){
        try{
            connection = DriverManager.getConnection(DATABASE_PATH+DATABASE_NAME);
            Log.print("DATABASE SUCCESSFULLY OPENED!");
            return true;
        } catch (SQLException throwables) {
            Log.print(throwables);
            return false;
        }
    }
    public void close(){
        try{
            connection.close();
            Log.print("Connection Closed!");
        } catch (SQLException throwables) {
            Log.print(throwables);
        }
    }
    public List<Car> getAllCars(){
        List<Car> cars = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_CARS);
            while(resultSet.next()){
                cars.add(createCarFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cars;
    }
    public Car createCarFromResultSet(ResultSet resultSet){
        Car c = new Car();
        try {
            c.setRegistrationNumber(resultSet.getString(2));
            c.setMake(resultSet.getString(3));
            c.setModel(resultSet.getString(4));
            c.setColorFromString(resultSet.getString(5));
            c.setYear(resultSet.getInt(6));
            c.setPrice(resultSet.getInt(7));
            c.setQuantity(resultSet.getInt(8));
            c.setLocation(resultSet.getString(9));
            return c;
        } catch (SQLException throwables) {
            Log.print(throwables);
            return null;
        }
    }

    public boolean insertNewCar(Car c){
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_CAR);
            statement.setString(1, c.getRegistrationNumber());
            statement.setString(2, c.getMake());
            statement.setString(3, c.getModel());
            statement.setString(4, c.getColorString());
            statement.setInt(5, c.getYear());
            statement.setInt(6, c.getPrice());
            statement.setInt(7, c.getQuantity());
            statement.setString(8, c.getLocation());
            return statement.execute();
        } catch (SQLException throwables) {
            Log.print(throwables);
            return false;
        }
    }

    public static void main(String[] args) {
        Database database = new Database();
        database.open();
//        Car car = new Car("ABC123", "Toyota", "Corolla", new String[]{"WHITE", "BLACK", "RED"}, 2020, 2200000, 2, "null");
//        database.insertNewCar(car);
        List<Car> cars = database.getAllCars();
        for(Car c: cars) System.out.println(c);
    }
}

class Log{
    public static void print(String message){
        System.out.println(message);
    }
    public static void print(Exception exception){
        exception.printStackTrace();
    }
}