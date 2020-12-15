package Database;

import Classes.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements Constants{
    Connection connection;
    private static Database database;
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
    public boolean updateCar(Car c){
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_CAR_FOR_REG);
            statement.setString(1, c.getMake());
            statement.setString(2, c.getModel());
            statement.setString(3, c.getColorString());
            statement.setInt(4, c.getYear());
            statement.setInt(5, c.getPrice());
            statement.setInt(6, c.getQuantity());
            statement.setString(7, c.getLocation());
            statement.setString(8, c.getRegistrationNumber());
            return statement.execute();
        } catch (SQLException throwables) {
            Log.print(throwables);
            return false;
        }
    }
    public int getIdForName(String name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_FOR_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = resultSet.getInt(1);
            int id = resultSet.getInt(2);
            return count == 0 ? -1:id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }
    public boolean checkUserIdAndPass(int id, String password){
        if(id == -1) return false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PASSWORD_FOR_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            int result = resultSet.getInt(1);
            return (result == 1) ? true : false;
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.out.println("Check Pass Failed...\n"+throwables.getMessage());
            return false;
        }
    }
    public static Database getInstance(){
        if(database == null) database = new Database();
        return database;
    }
    public static void main(String[] args) {
        Database database = new Database();
        database.open();
//        Car car = new Car("ABC123", "Toyota", "Corolla", new String[]{"WHITE", "BLACK", "RED"}, 2020, 2200000, 2, "null");
//        database.insertNewCar(car);
//        List<Car> cars = database.getAllCars();
//        for(int i=0; i<cars.size(); i++){
//            System.out.println(cars.get(i).getString());
//        }
//        Car car = cars.get(0);
////        database.insertNewCar(car);
//        car.setPrice(2000000);
//        System.out.println(car.getColorString());
//        database.updateCar(car);
//        List<Car> cars2 = database.getAllCars();
//        for(int i=0; i<cars.size(); i++){
//            System.out.println(cars.get(i).getString());
//            System.out.println(cars2.get(i).getString()+"\n");
//        }

        System.out.println(database.checkUserIdAndPass(database.getIdForName("Akash"), "akash"));

    }

    public void deleteCarForRegNum(String reg) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CAR_FOR_REG);
            statement.setString(1, reg);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
