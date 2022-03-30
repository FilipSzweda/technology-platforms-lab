package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TowersDatabase extends Database<Tower, String> {
    public TowersDatabase(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Tower.class);
    }

    public Tower findTower(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tower> list = entityManager.createQuery("SELECT p FROM " + classEntity.getSimpleName() +
                " p WHERE p.name = '" + name + "'", classEntity).getResultList();
        entityManager.close();
        if(list.size() > 0) return list.get(0);
        else return null;
    }

    public List<Tower> findAllLower(int height) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tower> towers = entityManager.createQuery("SELECT p FROM " + classEntity.getSimpleName() +
                " p WHERE p.height < " + height, classEntity).getResultList();
        entityManager.close();
        return towers;
    }
}
