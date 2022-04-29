package app;

public class Collection {
    public static void main(String[] args) {
        Collection collection = new Collection();
        Company company = new Company(args[0]);
        collection.initialize(company);
        company.printSet();
        System.out.printf("%n");
        company.printStatistics();
    }

    public void initialize(Company company){
        Employee employees[] = {
                new Employee("Stefan", "Department Director", "APD", 4, 16350),
                new Employee("Adam", "Tester", "ENT", 1, 6545.2),
                new Employee("Krzysztof", "Programmer", "AiD", 2, 9545),
                new Employee("Ola", "Architect", "ARC", 2, 8945.7),
                new Employee("Cezary", "Branch Chief", "GD", 5, 22545),
                new Employee("Igor", "Intern", "EXC", 0, 5545.5),
                new Employee("Jakub", "Programmer", "AiD", 1, 7545.3),
                new Employee("Anna", "Team Captain", "AiD", 3, 10545.5),
                new Employee("Maksymilian", "Team Captain", "EXC", 3, 10545),
                new Employee("Dawid", "Department Director", "WBD", 4, 14545.8)
        };
        for (Employee e : employees) {
            company.addEmployee(e);
        }
        employees[4].addSubordinate(employees[0]);
        employees[4].addSubordinate(employees[9]);
        employees[0].addSubordinate(employees[7]);
        employees[9].addSubordinate(employees[8]);
        employees[7].addSubordinate(employees[2]);
        employees[7].addSubordinate(employees[6]);
        employees[8].addSubordinate(employees[3]);
        employees[8].addSubordinate(employees[5]);
    }
}
