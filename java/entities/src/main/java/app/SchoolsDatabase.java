package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class SchoolsDatabase extends Database<School, String> {
    public SchoolsDatabase(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, School.class);
    }

    public School findSchool(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<School> list = entityManager.createQuery("SELECT p FROM " + classEntity.getSimpleName() +
                " p WHERE p.name = '" + name + "'", classEntity).getResultList();
        entityManager.close();
        if(list.size() > 0) return list.get(0);
        else return null;
    }

    public List<School> findAllHigher(int influence) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<School> schools = entityManager.createQuery("SELECT p FROM " + classEntity.getSimpleName() +
                " p WHERE p.influence > " + influence, classEntity).getResultList();
        entityManager.close();
        return schools;
    }
}
