package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TowersDatabase extends Database<Tower, String> {
    public TowersDatabase(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Tower.class);
    }

    public List<Tower> findAllWithBiggerBudget(int budget) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tower> list = entityManager.createQuery("SELECT br FROM " + classEntity.getSimpleName() +
                " br WHERE br.budget > " + budget, classEntity).getResultList();
        entityManager.close();
        return list;
    }

    public Tower findParticularTower(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tower> list = entityManager.createQuery("SELECT br FROM " + classEntity.getSimpleName() +
                " br WHERE br.name = '" + name + "'", classEntity).getResultList();
        entityManager.close();
        if(list.size() > 0) return list.get(0);
        else return null;
    }
}
