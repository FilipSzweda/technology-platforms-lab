package app;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class School {
    @Id
    private String name;
    private int influence;

    @OneToMany(mappedBy="school", fetch= FetchType.EAGER)
    private List<Wizard> wizards = new ArrayList<>();
    public void addWizard(Wizard wizard) { wizards.add(wizard); }

    public School(){}
    public School(String name, int influence){
        this.name = name;
        this.influence = influence;
    }

    public String getName(){ return this.name; }

    @PreRemove
    private void preRemove(){ for (Wizard wizard : wizards) wizard.setSchool(null); }

    @Override
    public String toString(){ return name; }
}