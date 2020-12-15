package Main;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestClass implements Runnable{
    Thread thread;
    ServerSocket ss;
    public TestClass() throws IOException {
//        ss = new ServerSocket(11111);
    }
    public void startThread() throws IOException {
        ss = new ServerSocket(11111);
        thread = new Thread(this);
        thread.start();
    }
    public void closeThread() throws IOException {
        ss.close();
    }
    @Override
    public void run() {
        while(true){
            try {
                Socket socket = ss.accept();
                System.out.println("New Connection");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        int n=5;
        TestClass tc = new TestClass();
        tc.startThread();
        Thread.sleep(5000);
        tc.closeThread();
        tc.startThread();
        Socket socket = new Socket("localhost", 11111);
    }
}
