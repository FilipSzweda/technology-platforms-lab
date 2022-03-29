package app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entities");
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
                3. Delete a tower
                4. Show all towers and mages
                5. Show all mages with higher level than X
                6. Show a tower with level of mage higher than X
                7. Show all towers lower than X
                8. Exit"""
            );
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option) {
                case 1 -> {
                    System.out.print("New mage name: ");
                    String newMageName = input.next();

                    System.out.print("New mage level: ");
                    int newMageLevel = input.nextInt();

                    Mage newMage = new Mage(newMageName, newMageLevel);

                    System.out.print("New mage tower (enter 'null' if there's none): ");
                    String newMageTower = input.next();

                    if (newMageTower.equals("null")) {
                        newMage.setTower(null);
                    } else {
                        Tower tower1 = towersDatabase.findTower(newMageTower);
                        newMage.setTower(tower1);
                    }

                    magesDatabase.add(newMage);
                }
                case 2 -> {
                    System.out.print("New tower name: ");
                    String newTowerName = input.next();

                    System.out.print("New tower height: ");
                    int newTowerHeight = input.nextInt();

                    towersDatabase.add(new Tower(newTowerName, newTowerHeight));
                }
                case 3 -> {
                    System.out.print("Name of the tower to be deleted: ");
                    String towerToDeleteName = input.next();

                    Tower towerToDelete = towersDatabase.findTower(towerToDeleteName);

                    if (towerToDelete == null) {
                        System.out.println("No tower with name '" + towerToDeleteName + "'\n");
                    } else {
                        towersDatabase.removeEntity(towerToDelete);
                        System.out.println("Tower removed");
                    }
                }
                case 4 -> {
                    List<Tower> towers = towersDatabase.findAll();
                    List<Mage> mages = magesDatabase.findAll();

                    System.out.println("All towers:");
                    for (Tower tower : towers) System.out.println(tower);
                    System.out.println("All mages:");
                    for (Mage mage : mages) System.out.println(mage);
                }
                case 5 -> {
                    System.out.print("Level X: ");
                    int level = input.nextInt();

                    List<Mage> magesWithHigherLevel = magesDatabase.findAllWithHigherLevel(level);

                    System.out.println("Mages with level higher than " + level + ": ");
                    for (Mage mage : magesWithHigherLevel) System.out.println(mage);
                }
                case 6 -> {
                    System.out.print("Tower name: ");
                    String towerToShowName = input.next();

                    Tower towerToShow = towersDatabase.findTower(towerToShowName);

                    if (towerToShow == null) {
                        System.out.println("No tower with name '" + towerToShowName + "'\n");
                    } else {
                        System.out.print("Level X: ");
                        int levelToShow = input.nextInt();

                        List<Mage> magesToShow = magesDatabase.findAllWithHigherLevel(levelToShow, towerToShow);

                        for (Mage mage : magesToShow) System.out.println(mage);
                    }
                }
                case 7 -> {
                    System.out.print("Height X: ");
                    int height = input.nextInt();

                    List<Tower> lowerTowers = towersDatabase.findAllLower(height);

                    System.out.println("Towers lower than " + height + ": ");
                    for (Tower tower : lowerTowers) System.out.println(tower);
                }
                case 8 -> exit = true;
                default -> System.out.println("Invalid option");
            }
        }
    }
}
