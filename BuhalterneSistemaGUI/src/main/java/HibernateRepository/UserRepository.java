package HibernateRepository;

import dataStructures.Category;
import dataStructures.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserRepository {

    private final EntityManagerFactory entityManagerFactory;

    public UserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(User user) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(user));
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void edit(User user) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            // entityManager.clear();
        } catch (Exception e) {
            e.printStackTrace();

            throw e;
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
            User user = entityManager.find(User.class, id);

            for (Category category : user.getCategories()) {
                if (category.getResponsiblePerson().size() == 1) {
                    entityManager.remove(category);
                }
            }

            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<User> getUsers() {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            CriteriaQuery<Object> criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(User.class));
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

    public User getUserById(int id) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            return entityManager.find(User.class, id);
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
