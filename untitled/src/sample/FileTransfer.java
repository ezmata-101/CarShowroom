package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileTransfer {
    Socket socket;
    Queue<File> fileQueue = new LinkedList<>();
    DataOutputStream dos;
    DataInputStream dis;
    boolean isSending = false;
    boolean isReceiving = false;
    final int CHUNK_SIZE = 1024*1024;
    FileTransfer(Socket socket){
        this.socket = socket;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addToFile(List<File> files){
        for(File file: files)addToFile(file);
    }
    public void addToFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files) addToFile(file);
        }
        if(file.isFile()) fileQueue.add(file);
        System.out.println("SIZE: "+fileQueue.size());
    }
    public void startSending() throws IOException {
        isSending = true;
        dos.writeUTF("FILE_SENDING_STARTED");
        new Thread(() -> {
            while(!fileQueue.isEmpty()){
                File file = fileQueue.peek();
                try {
                    sendFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to send file: "+file.getName());
                }
                fileQueue.remove();
            }
            try {
                dos.writeUTF("FILE_SENDING_COMPLETED");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void sendFile(File file) throws IOException{
        dos.writeUTF("FILE_NAME/"+file.getName());
        long fileSize = file.length();
        Update update = new Update(fileSize);
        update.startThread();
        dos.writeLong(fileSize);
        System.out.println("FILE SIZE: "+fileSize);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[CHUNK_SIZE];
        int n = -1;
        while(true){
            n = bis.read(buffer, 0, CHUNK_SIZE);
            update.addToProgress(n);
            if(n == -1) break;
            dos.write(buffer, 0, n);
        }
        System.out.println("Closed!");
        update.closeThread();
        dos.flush();
        bis.close();
        isSending = false;
    }
    public void startReceiving(){
        isReceiving = true;
        new Thread(() -> {
            String s = null;
            try {
                s = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            while(true){
                try {
                    if(s.equals("FILE_SENDING_COMPLETED")) break;
                    receiveFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    private void receiveFile() throws IOException{
        String message = dis.readUTF();
        System.out.println(message);
        String[] ss = message.split("/");
        if(!ss[0].equals("FILE_NAME")) return;
        File file = new File(System.getProperty("user.dir") + File.separator + ss[1]);
        file.createNewFile();
        System.out.println("Created New File: "+file.getAbsolutePath());
        long totalLength = dis.readLong();
        System.out.println("FILE SIZE: "+totalLength);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buffer = new byte[CHUNK_SIZE];
        while(totalLength > 0){
            int n = dis.read(buffer, 0, CHUNK_SIZE);
            bos.write(buffer, 0, n);
            totalLength -= n;
        }
        bos.close();
        System.out.println("Completed");
    }

    public static void main2(String[] args) {
        System.out.println();
        File file = new File(System.getProperty("user.dir")+File.separator+"Received" + File.separator+"AkashBatash123");
        try{
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(file.exists())System.out.println(file.getAbsolutePath());
        else System.out.println("NULL");
    }
}
