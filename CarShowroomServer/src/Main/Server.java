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
    private List<String> manufacturers;
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
            manufacturers = database.getAllManufacturers();
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
            if(c.getMode() != Client.ADMIN)c.send(s);
        }
    }

    public void requestLogin(String name, String password, Client client) {
        int id = database.getIdForName(name);
        if(id != -1){
            if(database.checkUserIdAndPass(id, password)){
                client.setMode(Client.MANUFACTURER);
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
            if(c.getMode() != Client.ADMIN)c.send(message);
        }
    }

    public void handleSignUp(String userName, String password, Client client) {
        int id = database.getIdForName(userName);
        if(id != -1) client.send("signUp/failed/User Name Already Exists. Try logging in.");
        else{
            if(database.insertNewManufacturer(userName, password)){
                client.send("signUp/successful");
            }else client.send("signUp/failed/Server Error!");
        }
    }

    public void sendAllManufacturers(Client client) {
        for(String s:manufacturers){
            client.send("MANUFACTURER/"+s+"/"+s);
        }
    }

    public void deleteManufacturer(String name) {
        database.deleteManufacturer(name);
        sendToAllAdmin("MANUFACTURER/DELETE/"+name);
        manufacturers.remove(name);
    }

    private void sendToAllAdmin(String message){
        for(Client c: clients){
            if(c.getMode() == Client.ADMIN){
                c.send(message);
            }
        }
    }

    public void updateManufacturer(String prevName, String currentName, String currentPass, Client client) {
        for(int i=0; i<manufacturers.size(); i++){
            if(prevName.equals(manufacturers.get(i))) {
                manufacturers.remove(i);
                manufacturers.add(currentName);
                break;
            }
        }
        int id = database.getIdForName(prevName);
        if(id == -1) {
            client.send("MANUFACTURER/FAILED/NO SUCH USER");
            return;
        }
        database.updateManufacturer(id, currentName, currentPass);
        sendToAllAdmin("MANUFACTURER/"+prevName+"/"+currentName);
        manufacturers.remove(prevName);
        manufacturers.add(currentName);
    }

    public void addManufacturer(String name, String pass, Client client) {
        int id = database.getIdForName(name);
        if(id != -1) {
            client.send("MANUFACTURER/FAILED/Already exists");
        }
        database.insertNewManufacturer(name, pass);
        sendToAllAdmin("MANUFACTURER/"+name+"/"+name);
        manufacturers.add(name);
    }
}
