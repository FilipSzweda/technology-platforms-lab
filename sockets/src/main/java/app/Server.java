package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4999);
            // serverSocket.setSoTimeout(2000);
            while(true) {
                Socket socket = serverSocket.accept();
                new Thread(new SocketHandling(socket)).start();
            }
        } catch (IOException except){
            except.printStackTrace();
        }
    }

}