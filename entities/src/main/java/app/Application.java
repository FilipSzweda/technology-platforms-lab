package app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        MagesDatabase magesDatabase = new MagesDatabase(entityManagerFactory);
        TowersDatabase towersDatabase = new TowersDatabase(entityManagerFactory);
        init(magesDatabase, towersDatabase);
    }

    public static void init(MagesDatabase magesDatabase, TowersDatabase towersDatabase){
        Scanner input = new Scanner(System.in);
        System.out.println(
            "1. Add mage\n" +
            "2. Add tower\n" +
            "3. Show all mages with higher level than X\n" +
            "4. Show all towers and mages\n" +
            "5. Delete a tower\n" +
            "6. Show a tower with level of mage less than X\n" +
            "7. Exit"
        );
        int inp = input.nextInt();
        while(inp != 7) {
            switch (inp) {
                case 1:
                    System.out.print("New mage name:");
                    String newMageName = input.next();
                    System.out.print("New mage level:");
                    int newLevel = input.nextInt();
                    Mage newMage = new Mage(newMageName, newLevel);
                    System.out.print("New mage tower (input 'null' if there's none):");
                    String mageTower = input.next();
                    if(mageTower != "null"){
                        Tower particular = towersDatabase.findParticularTower(mageTower);
                        newMage.setTower(particular);
                    }
                    else {
                        newMage.setTower(null);
                    }
                    magesDatabase.add(newMage);
                    break;
                case 2:
                    System.out.print("New tower name:");
                    String newTowerName = input.next();
                    System.out.print("New tower height:");
                    int newHeight = input.nextInt();
                    Tower newTower = new Tower(newTowerName, newHeight);
                    towersDatabase.add(newTower);
                    break;
                case 3:
                    System.out.print("Level X:");
                    int level = input.nextInt();
                    List<Mage> mages = magesDatabase.findAllWithHigherLevel(level);
                    System.out.println("Mages with level higher than " + level + ":");
                    for(int i = 0; i < mages.size(); i++) System.out.println(mages.get(i));
                    break;
                case 4:
                    System.out.println("All towers: ");
                    List<Tower> towers = towersDatabase.findAll();
                    for(int i = 0; i < towers.size(); i++) System.out.println(towers.get(i));
                    System.out.println("All mages: ");
                    List<Mage> mages2 = magesDatabase.findAll();
                    for(int i = 0; i < mages2.size(); i++) System.out.println(mages2.get(i));
                    break;
                case 5:
                    System.out.print("Name of the tower to be deleted: ");
                    String towerToDelete = input.next();
                    Tower particular2 = towersDatabase.findParticularTower(towerToDelete);
                    if(particular2 == null) {
                        System.out.println("No tower with name '" + towerToDelete + "'");
                    }
                    else {
                        towersDatabase.removeEntity(particular2);
                        System.out.println("Tower was burned down.");
                    }
                    break;
                case 6:
                    System.out.print("Which Tower are we talking about: ");
                    String towerToShow = input.next();
                    Tower particular3 = towersDatabase.findParticularTower(towerToShow);
                    if(particular3 == null) System.out.println("No tower with name '" + towerToShow + "'");
                    else{
                        System.out.print("Level X:");
                        int levelToShow = input.nextInt();
                        List<Mage> magesToShow = magesDatabase.findAllLessExpensiveThan(levelToShow, particular3);
                        for(int i = 0; i < magesToShow.size(); i++) System.out.println(magesToShow.get(i));
                    }
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
            System.out.println(
                "1. Add mage\n" +
                "2. Add tower\n" +
                "3. Show all mages with higher level than X\n" +
                "4. Show all towers and mages\n" +
                "5. Delete a tower\n" +
                "6. Show a tower with level of mage less than X\n" +
                "7. Exit"
            );
            inp = input.nextInt();
        }
    }
}
