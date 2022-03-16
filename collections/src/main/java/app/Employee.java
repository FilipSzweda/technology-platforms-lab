package app;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Employee implements Comparable <Employee> {
    private String name;
    private String jobTitle;
    private String department;
    private Integer clearanceLevel;
    private Double salary;
    private Set<Employee> subordinates;

    public Employee(String name, String jobTitle, String department, Integer clearanceLevel, double salary){
        this.name = name;
        this.jobTitle = jobTitle;
        this.department = department;
        this.clearanceLevel = clearanceLevel;
        this.salary = salary;
        this.subordinates = new TreeSet<>();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", department='" + department + '\'' +
                ", clearanceLevel=" + clearanceLevel +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return name.equals(employee.name) && jobTitle.equals(employee.jobTitle) && department.equals(employee.department) && clearanceLevel.equals(employee.clearanceLevel) && salary.equals(employee.salary) && Objects.equals(subordinates, employee.subordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, jobTitle, department, clearanceLevel, salary, subordinates);
    }

    @Override
    public int compareTo(Employee e) {
        int result = this.name.compareTo(e.name);
        if (result != 0){
            return result;
        }
        result = clearanceLevel - e.clearanceLevel;
        if(result != 0){
            return result;
        }
        result = Double.compare(this.salary, e.salary);
        return result;
    }

    public void addSubordinate(Employee e){
        this.subordinates.add(e);
    }

    public void print(String sign){
        System.out.print(sign);
        System.out.println(this);
        for (Employee e : subordinates){
            e.print(sign+sign.charAt(0));
        }
    }

    public String getName() {
        return name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getClearanceLevel() {
        return clearanceLevel;
    }

    public Double getSalary() {
        return salary;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }
}