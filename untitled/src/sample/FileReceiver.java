package sample;

import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(11111);
        Socket socket = serverSocket.accept();
        System.out.println("Someone connected!");
        FileTransfer fileTransfer = new FileTransfer(socket);
        fileTransfer.startReceiving();
    }
}
