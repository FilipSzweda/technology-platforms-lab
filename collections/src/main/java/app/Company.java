package app;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.TreeMap;

public class Company {
    private String sortType;
    private Set<Employee> employees;
    private Map<Employee, Integer> statistics;

    public Company(String _sort) {
        this.sortType = _sort;
        switch(sortType) {
            case "no_sorting" -> {
                System.out.println("No sorting:");
                this.employees = new HashSet<>();
            }
            case "natural_sorting" -> {
                System.out.println("Natural sorting:");
                this.employees = new TreeSet<>();
            }
            case "alternative_sorting" -> {
                System.out.println("Alternative sorting:");
                this.employees = new TreeSet<>(new EmployeeCompare());
            }
        }
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    private void DFS(Set<Employee> subordinates, Employee e) {
        for (Employee subordinate : e.getSubordinates()) {
            subordinates.add(subordinate);
            DFS(subordinates, subordinate);
        }
    }

    public void printSet() {
        Set<Employee> subordinates = new TreeSet<>();
        for (Employee e : employees) {
            DFS(subordinates, e);
        }
        for (Employee e : employees) {
            if (!subordinates.contains(e)) {
                e.print("-");
            }
        }
    }

    public void printStatistics() {
        this.statistics = this.sortType == "no_sorting" ? new HashMap<>() : new TreeMap<>();
        for (Employee e : employees) {
            Set<Employee> subordinates = new HashSet<>();
            DFS(subordinates, e);
            int size = subordinates.size();
            statistics.put(e, size);
        }
        for (Employee e : statistics.keySet()){
            System.out.println(e + " : " + statistics.get(e));
        }
    }
}
