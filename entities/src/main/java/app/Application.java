package app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entities");
        WizardsDatabase wizardsDatabase = new WizardsDatabase(entityManagerFactory);
        SchoolsDatabase schoolsDatabase = new SchoolsDatabase(entityManagerFactory);
        init(wizardsDatabase, schoolsDatabase);
    }

    public static void init(WizardsDatabase wizardsDatabase, SchoolsDatabase schoolsDatabase){
        boolean exit = false;
        while(!exit) {
            System.out.println(
                """
                
                1. Add wizard
                2. Add school
                3. Delete a school
                4. Show all schools and wizards
                5. Show all wizards with higher level than X
                6. Show wizards from school X with level higher than Y
                7. Show all schools with influence higher than X
                8. Exit"""
            );
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option) {
                case 1 -> {
                    System.out.print("New wizard name: ");
                    String newWizardName = input.next();

                    if(wizardsDatabase.findByName(newWizardName).size() == 0) {
                        System.out.print("New wizard level: ");
                        int newWizardLevel = input.nextInt();

                        Wizard newWizard = new Wizard(newWizardName, newWizardLevel);

                        System.out.print("New wizard school (enter 'none' if there's none): ");
                        String newWizardSchool = input.next();

                        if (!newWizardSchool.equals("none")) {
                            School school1 = schoolsDatabase.findSchool(newWizardSchool);
                            newWizard.setSchool(school1);
                        }

                        wizardsDatabase.add(newWizard);
                    } else {
                        System.out.print("Can't use this name\n");
                    }
                }
                case 2 -> {
                    System.out.print("New school name: ");
                    String newSchoolName = input.next();

                    if(schoolsDatabase.findByName(newSchoolName).size() == 0) {
                        System.out.print("New school influence: ");
                        int newSchoolInfluence = input.nextInt();

                        schoolsDatabase.add(new School(newSchoolName, newSchoolInfluence));
                    } else {
                        System.out.print("Can't use this name\n");
                    }
                }
                case 3 -> {
                    System.out.print("Name of the school to be deleted: ");
                    String schoolToDeleteName = input.next();

                    School schoolToDelete = schoolsDatabase.findSchool(schoolToDeleteName);

                    if (schoolToDelete == null) {
                        System.out.println("No school with name '" + schoolToDeleteName + "'\n");
                    } else {
                        schoolsDatabase.removeEntity(schoolToDelete);
                        System.out.println("School removed");
                    }
                }
                case 4 -> {
                    List<School> schools = schoolsDatabase.findAll();
                    List<Wizard> wizards = wizardsDatabase.findAll();

                    System.out.println("All schools:");
                    for (School school : schools) System.out.println(school);
                    System.out.println("All wizards:");
                    for (Wizard wizard : wizards) System.out.println(wizard);
                }
                case 5 -> {
                    System.out.print("Level X: ");
                    int level = input.nextInt();

                    List<Wizard> wizardsWithHigherLevel = wizardsDatabase.findAllWithHigherLevel(level);

                    System.out.println("Wizards with level higher than " + level + ": ");
                    for (Wizard wizard : wizardsWithHigherLevel) System.out.println(wizard);
                }
                case 6 -> {
                    System.out.print("School X: ");
                    String schoolToShowName = input.next();

                    School schoolToShow = schoolsDatabase.findSchool(schoolToShowName);

                    if (schoolToShow == null) {
                        System.out.println("No school with name '" + schoolToShowName + "'\n");
                    } else {
                        System.out.print("Level Y: ");
                        int levelToShow = input.nextInt();

                        List<Wizard> wizardsToShow = wizardsDatabase.findAllWithHigherLevel(levelToShow, schoolToShow);

                        for (Wizard wizard : wizardsToShow) System.out.println(wizard.getName());
                    }
                }
                case 7 -> {
                    System.out.print("Influence X: ");
                    int influence = input.nextInt();

                    List<School> higherSchools = schoolsDatabase.findAllHigher(influence);

                    System.out.println("Schools with influence higher than " + influence + ": ");
                    for (School school : higherSchools) System.out.println(school.getName());
                }
                case 8 -> exit = true;
                default -> System.out.println("Invalid option");
            }
        }
    }
}
