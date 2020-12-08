package HibernateRepository;

import dataStructures.Income;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class IncomeRepository {
    private final EntityManagerFactory entityManagerFactory;

    public IncomeRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(Income income) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(income));
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(Income income) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            income = entityManager.merge(income);
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
            EntityTransaction et = entityManager.getTransaction();

            et.begin();

            Income income = entityManager.find(Income.class, id);

            entityManager.remove(income);

            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Income> getIncomes() {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            CriteriaQuery<Object> criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Income.class));
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

    public Income getIncomeById(int id) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();

            return entityManager.find(Income.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return null;
    }

    public List<Income> getIncomesByDate(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atStartOfDay();
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            String queryText = "FROM Income WHERE date > :from AND date < :to ORDER BY id DESC";
            Query query = entityManager.createQuery(queryText);
            query.setParameter("from", from);
            query.setParameter("to", to);

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
}