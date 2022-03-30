package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public abstract class Database<E, ID> {
    protected final EntityManagerFactory entityManagerFactory;
    protected final Class<E> classEntity;

    public Database(EntityManagerFactory entityManagerFactory, Class<E> classEntity){
        this.entityManagerFactory = entityManagerFactory;
        this.classEntity = classEntity;
    }

    public void add(E entity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();
        entityManager.close();
    }

    public List<E> findAll(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<E> list = entityManager.createQuery("SELECT p FROM " + classEntity.getSimpleName() + " p", classEntity).getResultList();
        entityManager.close();
        return list;
    }

    public void removeEntity(E entity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(entityManager.merge(entity));
        entityTransaction.commit();
        entityManager.close();
    }
}
