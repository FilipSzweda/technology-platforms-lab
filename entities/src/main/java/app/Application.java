package app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        MagesDatabase magesDatabase = new MagesDatabase(entityManagerFactory);
        TowersDatabase towersDatabase = new TowersDatabase(entityManagerFactory);
        init(magesDatabase, towersDatabase);
    }

    public static void init(MagesDatabase magesDatabase, TowersDatabase towersDatabase){
        boolean exit = false;
        while(!exit) {
            System.out.println(
                """
                
                1. Add mage
                2. Add tower
                3. Show all mages with higher level than X
                4. Show all towers and mages
                5. Delete a tower
                6. Show a tower with level of mage less than X
                7. Exit"""
            );
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option) {
                case 1 -> {
                    System.out.print("New mage name: ");
                    String newMageName = input.next();
                    System.out.print("New mage level: ");
                    int newLevel = input.nextInt();
                    Mage newMage = new Mage(newMageName, newLevel);
                    System.out.print("New mage tower (enter 'null' if there's none): ");
                    String mageTower = input.next();
                    if (mageTower.equals("null")) {
                        newMage.setTower(null);
                    } else {
                        Tower tower1 = towersDatabase.findParticularTower(mageTower);
                        newMage.setTower(tower1);
                    }
                    magesDatabase.add(newMage);
                }
                case 2 -> {
                    System.out.print("New tower name: ");
                    String newTowerName = input.next();
                    System.out.print("New tower height: ");
                    int newHeight = input.nextInt();
                    towersDatabase.add(new Tower(newTowerName, newHeight));
                }
                case 3 -> {
                    System.out.print("Level X: ");
                    int level = input.nextInt();
                    List<Mage> mages = magesDatabase.findAllWithHigherLevel(level);

                    System.out.println("Mages with level higher than " + level + ": ");
                    for (Mage mage : mages) System.out.println(mage);
                }
                case 4 -> {
                    List<Tower> towers = towersDatabase.findAll();
                    List<Mage> mages2 = magesDatabase.findAll();

                    System.out.println("All towers:");
                    for (Tower tower : towers) System.out.println(tower);
                    System.out.println("All mages:");
                    for (Mage mage : mages2) System.out.println(mage);
                }
                case 5 -> {
                    System.out.print("Name of the tower to be deleted: ");
                    String towerToDelete = input.next();
                    Tower tower2 = towersDatabase.findParticularTower(towerToDelete);

                    if (tower2 == null) {
                        System.out.println("No tower with name '" + towerToDelete + "'\n");
                    } else {
                        towersDatabase.removeEntity(tower2);
                        System.out.println("Tower removed");
                    }
                }
                case 6 -> {
                    System.out.print("Tower name: ");
                    String towerToShow = input.next();
                    Tower tower3 = towersDatabase.findParticularTower(towerToShow);

                    if (tower3 == null) {
                        System.out.println("No tower with name '" + towerToShow + "'\n");
                    } else {
                        System.out.print("Level X: ");
                        int levelToShow = input.nextInt();
                        List<Mage> magesToShow = magesDatabase.findAllLessExpensiveThan(levelToShow, tower3);
                        for (Mage mage : magesToShow) System.out.println(mage);
                    }
                }
                case 7 -> exit = true;
                default -> System.out.println("Invalid option");
            }
        }
    }
}
