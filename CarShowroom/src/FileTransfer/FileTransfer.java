package FileTransfer;

import java.io.*;
import java.net.Socket;
import java.sql.Time;

public class FileTransfer {
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final int CHUNK_SIZE = 1024*1024;
    public FileTransfer(Socket s){
        this.socket = s;
        try{
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void requestSendFile(File file){
        try {
            System.out.println("Wrote: req");
            dos.writeUTF("UPLOAD_REQUEST/"+file.getName());
            System.out.println("Current time: "+System.currentTimeMillis());
            String response = dis.readUTF();
            System.out.println("Read: "+response);
            if(response.equals("UPLOAD_DENIED")) return;
            sendFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void requestToSendFile(String fileName){
        try {
            dos.writeUTF("REQUEST_TO_SEND_FILE/"+fileName);
            String response = dis.readUTF();
            if(response.equals("REQUEST_DENIED")) return;
            receiveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void responseToReceiveFile(String fileName){
        File file = new File("src/CarImages/"+fileName);
        try{
            if(file.exists() && file.length() > 0){
                dos.writeUTF("UPLOAD_DENIED");
                return;
            }else{
                dos.writeUTF("UPLOAD_REQUEST_ACCEPTED");
                receiveFile();
            }
        }catch (IOException e){}
    }
    public void sendFile(File file) throws IOException{
        System.out.println("Sending File!");
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
    }

    public void receiveFile() throws IOException{
        String message = dis.readUTF();
        System.out.println(message);
        String[] ss = message.split("/");
        if(!ss[0].equals("FILE_NAME")) return;
        File file = new File("src/CarImages/"+ss[1]);
        file.createNewFile();
        System.out.println("Created New File: "+file.getAbsolutePath());
        long totalLength = dis.readLong();
//        Update update = new Update(totalLength);
//        update.startThread();
        System.out.println("FILE SIZE: "+totalLength);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buffer = new byte[CHUNK_SIZE];
        while(totalLength > 0){
            int n = dis.read(buffer, 0, CHUNK_SIZE);
            bos.write(buffer, 0, n);
            totalLength -= n;
//            update.addToProgress(n);
        }
//        update.closeThread();
        bos.flush();
        bos.close();
        System.out.println("File Transfer Completed "+file.length());
    }
}
