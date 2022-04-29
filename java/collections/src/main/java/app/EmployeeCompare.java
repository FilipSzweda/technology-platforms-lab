package app;

import java.util.Comparator;

public class EmployeeCompare implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        int result = e1.getJobTitle().compareTo(e2.getJobTitle());
        if (result != 0){
            return result;
        }
        result = Double.compare(e1.getSalary(), e2.getSalary());
        if (result != 0){
            return result;
        }
        result = e1.getName().compareTo(e2.getName());
        if (result != 0){
            return result;
        }
        result = e1.getDepartment().compareTo(e2.getDepartment());
        if (result != 0){
            return result;
        }
        result = Integer.compare(e1.getClearanceLevel(), e2.getClearanceLevel());
        return result;
    }
}
