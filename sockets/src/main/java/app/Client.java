package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    String attemptConnection(int messagesNumber) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket socket = new Socket(address, 4999);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String response = (String) in.readObject();
            if(!response.equals("ready")) {
                return "error: server not ready";
            }

            out.writeObject(messagesNumber);
            response = (String) in.readObject();
            if(!response.equals("ready for messages")) {
                return "error: server not ready for messages";
            }

            out.writeObject(new Message((messagesNumber)));

            response = (String) in.readObject();
            if(!response.equals("finished")) {
                return "error: server not finished";
            }

            return response;
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return "error: other";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();
        boolean exit = false;
        while(!exit) {
            System.out.print("Type a number or type 'exit' if you want to exit the program:\n");
            String line = scanner.nextLine();
            if(line.equals("exit")) {
                exit = true;
            } else {
                System.out.println(client.attemptConnection(Integer.parseInt(line)));
            }
        }
    }
}