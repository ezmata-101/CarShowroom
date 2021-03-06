package Main;

import Controller.Controller;
import Controller.MainController;
import Controller.AdminController;
import FileTransfer.FileTransfer;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
    private Main main;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Thread thread;
    private MainController mainController;
    private AdminController admin;

    private static Client client;
    private Client(){
    }

    public static Client getInstance(Main main){
        if(client == null){
            client = new Client();
            client.main = main;
            client.connectToServer();
        }
        return client;
    }
    public static Client getInstance(){
        return client;
    }

    private void connectToServer() {
        try{
            socket = new Socket("localhost", 11111);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            thread = new Thread(this);
            thread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changePane(String fxml, String stageName){
        main.changePane(fxml, stageName, this);
    }

    private void handleMessage(String message) {
        String ss[] = message.split("/");
//        System.out.println(message);
        if(ss[0].equals("admin")){
            Platform.runLater(()->main.changePane("admin", "Car Warehouse (ADMIN)", this));
        }
        if(ss[0].equals("MANUFACTURER")) {
            if(ss[1].equals("DELETE")) Platform.runLater(() -> admin.delete(ss[2]));
            else if(ss[1].equals("FAILED")) return;
            else Platform.runLater(() -> admin.addOrEdit(ss[1], ss[2]));
        }
        if(ss[0].equals("login") || ss[0].equals("signUp")){
            if(ss[1].equalsIgnoreCase("successful")){
                Platform.runLater(() -> {
                    main.changePane("main", "Car Warehouse ("+ ss[2].toUpperCase() +")", this);
                    mainController.setIsManufacturer(ss[2].equalsIgnoreCase("manufacturer"));
                });
            }
            else{
                main.setNotification(ss[2]);
            }
        }
        if(ss[0].equals("CAR_OBJECT")){
            Platform.runLater(() -> mainController.createOrEditCar(message, ss[1]));
        }
        if(ss[0].equals("DELETE_CAR")){
            Platform.runLater(() -> mainController.deleteCar(ss[1]));
        }
        if(ss[0].equals("CAR_IMAGE")){
            FileTransfer ft = new FileTransfer(socket);
            try {
                ft.receiveFile();
                requestCars();
                Platform.runLater(()->{
                    mainController.updateCars();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendMessage(String message){
//        System.out.println("Wrote: "+message);
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setMainController(MainController mainController){
        this.mainController = mainController;
        requestCars();
    }

    private void requestCars() {
//        System.out.println("REQUESTED CARS");
        sendMessage("request:sendCars");
    }

    @Override
    public void run() {
        while(true){
            try {
                String message = dataInputStream.readUTF();
                handleMessage(message);
            } catch (IOException e) {
                break;
            }
        }
    }

    public void setMainController(Controller currentController) {
        MainController controller = (MainController) currentController;
        controller.setDetailViews();
        try{
            setMainController(controller);
        }catch (Exception e){
//            System.out.println("Exception Occurs");
        }
    }

    public void sendFile(File file) {
        sendMessage("SENDING_IMAGE");
        FileTransfer ft = new FileTransfer(socket);
        try {
            ft.sendFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void disconnect(){
        try{
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
        }
    }
    public void setAdmin(Controller currentController) {
        admin = (AdminController) currentController;
        sendMessage("request:manufacturers");
    }
}
