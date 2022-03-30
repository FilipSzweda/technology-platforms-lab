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
        if(school != null){
            this.school = school;
            school.addWizard(this);
        }
    }

    @Override
    public String toString(){
        return name;
    }
}