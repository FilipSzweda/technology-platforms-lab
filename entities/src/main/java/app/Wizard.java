package app;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Wizard {
    @Id
    private String name;
    private int level;

    @ManyToOne
    @JoinColumn(name = "school")
    private School school = null;

    public Wizard(){}
    public Wizard(String name, int level){
        this.name = name;
        this.level = level;
    }

    public void setSchool(School school){
        this.school = school;
        if(school != null){
            school.addWizard(this);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        if(this.school != null) {
            return "Wizard - name: " + name + ", level: " + level + ", school name: " + this.school.getName();
        } else {
            return "Wizard - name: " + name + ", level: " + level + ", no school";
        }
    }
}