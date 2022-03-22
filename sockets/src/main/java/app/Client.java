package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Integer port = 4999;

    String createRequest(int n) {
        try {
            InetAddress address = InetAddress.getLocalHost();

            Socket socket = new Socket(address, this.port);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            String response = (String) inputStream.readObject();
            if(!response.equals("ready")) {
                return "error";
            }

            outputStream.writeObject(n);
            response = (String) inputStream.readObject();

            if(!response.equals("ready for messages")) {
                return "error";
            }

            for(int i = 0; i < n; i++)  {
                outputStream.writeObject(new Message((i+1), "some string"));
            }

            response = (String) inputStream.readObject();

            if(!response.equals("finished")) {
                return "error";
            }

            return response;
        } catch (IOException except){
            except.printStackTrace();
        } catch (ClassNotFoundException except) {
            except.printStackTrace();
        }
        return "error";
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();
        boolean i = true;
        while(i) {
            System.out.print("Messages number:");
            String line = scanner.nextLine();
            if(line.equals("exit")) {
                i = false;
                System.out.println("Client stopped working");
            }
            else {
                System.out.println(client.createRequest(Integer.parseInt(line)));
            }
        }
    }
}