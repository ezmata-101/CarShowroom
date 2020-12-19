package Main;

import Classes.Car;
import Database.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server{
    private ServerSocket serverSocket;
    private List<Client> clients;
    private static Server server;
    private Database database;
    private List<Car> cars;
    private Server (){
        database = Database.getInstance();
        clients = new ArrayList<>();
        cars = new ArrayList<>();
        startServer();
    }
    public static Server getInstance(){
        if(server == null) server = new Server();
        return server;
    }
    private void startServer(){
        try{
            database.open();
            cars = database.getAllCars();
            serverSocket = new ServerSocket(11111);
            getConnection();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private void getConnection() {
        while(true){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Added!");
                clients.add(new Client(this, socket, database));
            } catch (IOException e) {
//                e.printStackTrace();
                break;
            }
        }
    }

    public void editOrUpdateCar(String s, Client client) {
        Car car = new Car(s);
        boolean flag = false;
        for(Car c: cars){
            if(c.getRegistrationNumber().equalsIgnoreCase(car.getRegistrationNumber())){
                c.setFromString(s);
                System.out.println(database.updateCar(c));
                flag = true;
                break;
            }
        }
        if(!flag) {
            cars.add(car);
            database.insertNewCar(car);
        }
        for(Client c: clients){
            c.send(s);
        }
    }

    public void requestLogin(String name, String password, Client client) {
        int id = database.getIdForName(name);
        if(id != -1){
            if(database.checkUserIdAndPass(id, password)){
                client.send("login/successful/manufacturer");
            }else client.send("login/unsuccessful/Wrong Password!");
        }else client.send("login/unsuccessful/Manufacturer Not Found!");
    }
    public void sendAllCarsTo(Client client){
        for(Car car: cars){
            client.send(car.getString());
        }
    }

    public void removeCar(String message, String reg) {
        for(Car c: cars){
            if(c.getRegistrationNumber().equalsIgnoreCase(reg)){
                cars.remove(c);
                break;
            }
        }
        database.deleteCarForRegNum(reg);
        for(Client c:clients){
            c.send(message);
        }
    }
}
