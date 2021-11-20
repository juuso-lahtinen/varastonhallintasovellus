package r13.javafx.Varastonhallinta.models.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.User;

import java.util.List;


public class UserAccessObject {

    public static void main(String[] args) {
        removeUser("da7c9aa3-2705-4658-aaae-fc91d5da6f11");
    }

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");

    public boolean checkLogin(String username, String password) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String strQuery = "SELECT u FROM User u WHERE u.username=:username AND u.password=:password";

        TypedQuery<User> tq = em.createQuery(strQuery, User.class);
        tq.setParameter("username", username);
        tq.setParameter("password", password);

        if (tq.getResultList().size() == 1) {
            em.close();
            return true;
        } else {
            em.close();
            return false;
        }
    }

    public static User getDBUsername(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String strQuery = "SELECT u FROM User u WHERE u.username=:username";

        TypedQuery<User> tq = em.createQuery(strQuery, User.class);
        tq.setParameter("username", username);

        User user = null;
        try {
            user = tq.getSingleResult();

        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }

    public static User addUser(User user) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public static boolean removeUser(String id) {

        if (id == null || (getDBUsername(id) == null)) {
            return false;
        }

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "DELETE User u WHERE u.id = :id";

        try {

            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            System.out.println("removal success" + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("removal fail" + id);
            return false;
        } finally {
            em.close();
        }
    }

    public static List getUsers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT u FROM User u WHERE u.id IS NOT NULL";

        TypedQuery<User> tq = em.createQuery(query, User.class);
        List<User> users = null;

        try {
            users = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return users;
    }
}
