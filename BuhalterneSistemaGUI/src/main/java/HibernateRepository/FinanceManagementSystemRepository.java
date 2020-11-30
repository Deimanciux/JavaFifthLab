package HibernateRepository;

import dataStructures.FinanceManagementSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class FinanceManagementSystemRepository {
    private final EntityManagerFactory entityManagerFactory;

    public FinanceManagementSystemRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(FinanceManagementSystem fms) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(fms));
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(FinanceManagementSystem fms) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            fms = entityManager.merge(fms);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void remove(int id) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            FinanceManagementSystem fms = entityManager.find(FinanceManagementSystem.class, id);

            entityManager.remove(fms);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<FinanceManagementSystem> getAllFms() {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            CriteriaQuery<Object> criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(FinanceManagementSystem.class));
            Query query = entityManager.createQuery(criteriaQuery);

            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return null;
    }

    public FinanceManagementSystem getFmsById(int id) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            return entityManager.find(FinanceManagementSystem.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return null;
    }
}