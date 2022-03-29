package app;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;

    @OneToMany(mappedBy="tower", fetch= FetchType.EAGER)
    private List<Mage> mages = new ArrayList<>();
    public List<Mage> getMages(){ return this.mages; };
    public void addMage(Mage mage) { mages.add(mage); };

    public Tower(){};
    public Tower(String name, int height){
        this.name = name;
        this.height = height;
    }

    public String getName(){ return this.name; };

    @PreRemove
    private void preRemove(){ for (Mage mage : mages) mage.setTower(null); }

    @Override
    public String toString(){ return "Tower - name: " + name + ", height: " + height + "\n"; };
}