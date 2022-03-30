package app;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class WizardsDatabase extends Database<Wizard, String> {
    public WizardsDatabase(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Wizard.class);
    }

    public List<Wizard> findAllWithHigherLevel(int level){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Wizard> wizards = entityManager.createQuery("SELECT p FROM " + classEntity.getSimpleName() +
                " p WHERE p.level > " + level, classEntity).getResultList();
        entityManager.close();
        return wizards;
    }

    public List<Wizard> findAllWithHigherLevel(int level, School school){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Wizard> wizards = entityManager.createQuery("SELECT p FROM " + classEntity.getSimpleName() +
                " p WHERE p.level >" + level + " AND p.school = '" +
                school.getName() + "'", classEntity).getResultList();
        entityManager.close();
        return wizards;
    }
}
