package app;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Mage {
    @Id
    private String name;
    private int level;

    @ManyToOne
    @JoinColumn(name = "tower")
    private Tower tower = null;

    public Mage(){}
    public Mage(String name, int level){
        this.name = name;
        this.level = level;
    }

    public void setTower(Tower tower){
        this.tower = tower;
        if(tower != null){
            tower.addMage(this);
        }
    }

    @Override
    public String toString(){
        if(this.tower != null) {
            return "Mage - name: " + name + ", level: " + level + ", tower name: " + this.tower.getName();
        } else {
            return "Mage - name: " + name + ", level: " + level + ", no tower";
        }
    }
}