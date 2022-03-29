package app;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MagesDatabase extends Database<Mage, String> {
    public MagesDatabase(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Mage.class);
    }

    public List<Mage> findAllWithHigherLevel(int level){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Mage> list = entityManager.createQuery("SELECT b FROM " + classEntity.getSimpleName() +
                " b WHERE b.level > " + level, classEntity).getResultList();
        entityManager.close();
        return list;
    }

    public List<Mage> findAllWithHigherLevel(int level, Tower Tower){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Mage> list = entityManager.createQuery("SELECT b FROM " + classEntity.getSimpleName() +
                " b WHERE b.level >" + level + " AND Tower = '" +
                Tower.getName() + "'", classEntity).getResultList();
        entityManager.close();
        return list;
    }

    public List<Mage> findAllLessExpensiveThan(int level, Tower Tower){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Mage> list = entityManager.createQuery("SELECT b FROM " + classEntity.getSimpleName() +
                " b WHERE b.level <" + level + " AND Tower = '" +
                Tower.getName() + "'", classEntity).getResultList();
        entityManager.close();
        return list;
    }
}
