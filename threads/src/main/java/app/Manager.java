package app;

import java.io.IOException;
import java.util.Scanner;

public class Manager {
    public static void main (String[] args) throws IOException {
        int threadsNumber = Integer.parseInt(args[0]);
        boolean exit = false;

        Scanner scanner = new Scanner(System.in);

        Application application = new Application();
        application.createThreads(threadsNumber);
        application.createTasks();

        System.out.println("This application checks whether or not a number is a prime number.");
        options();
        while(!exit){
            switch (scanner.next()) {
                case "1" -> {
                    System.out.println("Type a number you want to check.");
                    int number = scanner.nextInt();
                    application.addTask(number);
                    System.out.println("Task added.");
                    options();
                }
                case "2" -> exit = true;
                default -> System.out.println("Invalid option.");
            }
        }

        scanner.close();
        application.stopThreads();
        application.writeResults();
    }

    public static void options(){
        System.out.println("Options:");
        System.out.println("1. Add task");
        System.out.println("2. Exit");
    }
}