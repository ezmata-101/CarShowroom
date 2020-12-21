package Main;

import Classes.Car;
import Database.Database;
import FileTransfer.FileTransfer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{
    private Server server;
    private Database database;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private boolean isOnline = false;
    private Thread thread;
    public static final int VIEWER = 1;
    public static final int MANUFACTURER = 2;
    public static final int ADMIN = 3;
    private int mode = 0;
    public Client(Server server, Socket socket, Database database) {
        this.server = server;
        this.socket = socket;
        this.database = database;
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            thread = new Thread(this);
            startThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startThread(){
        isOnline = true;
        thread.start();
    }

    private void handleMessage(String s) {
        System.out.println(s);
        String ss[] = s.split("/");
        if(ss[0].equals("CAR_OBJECT")) server.editOrUpdateCar(s, this);
        if(ss[0].equals("DELETE_CAR")) server.removeCar(s, ss[1]);
        if(ss[0].equals("login")){
            if(ss[1].equalsIgnoreCase("viewer")){
                send("login/successful/viewer");
                mode = VIEWER;
            }
            else server.requestLogin(ss[1], ss[2], this);
        }
        if(ss[0].equals("signUp")) server.handleSignUp(ss[1], ss[2], this);
        if(s.equals("login/admin/admin")){
            mode = ADMIN;
            send("admin/successful");
        }
        if(ss[0].equals("SENDING_IMAGE")) receiveFile();
        if(ss[0].equals("request:sendCars")) server.sendAllCarsTo(this);
        if(ss[0].equals("request:carImage")) sendImage(ss[1]);
        if(ss[0].equals("request:manufacturers")) server.sendAllManufacturers(this);
        if(ss[0].equals("MANUFACTURER") && ss[1].equals("DELETE")) server.deleteManufacturer(ss[2]);
        if(ss[0].equals("MANUFACTURER") && ss[1].equals("EDIT")) server.updateManufacturer(ss[2], ss[3], ss[4], this);
        if(ss[0].equals("MANUFACTURER") && ss[1].equals("ADD")) server.addManufacturer(ss[3], ss[4], this);
    }

    private void sendImage(String fileName) {
        File file = new File("src/CarImages/"+fileName);
        if(file.exists()){
            send("CAR_IMAGE");
            FileTransfer ft = new FileTransfer(socket);
            try {
                ft.sendFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveFile() {
        FileTransfer ft = new FileTransfer(socket);
        try {
            ft.receiveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread Started");
        while(true){
            try {
                String message = dataInputStream.readUTF();
                handleMessage(message);
            } catch (IOException e) {
//                e.printStackTrace();
                isOnline = false;
                break;
            }
        }
    }

    public void send(String s) {
        System.out.println("Sent: "+s);
        try{
            if(isOnline)dataOutputStream.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMode(){return mode;}

    public void setMode(int mode) {
        this.mode = mode;
    }
}
