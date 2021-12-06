package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Order;

import javax.persistence.*;
import java.util.List;

public class OrderAccessObject {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    public static void main(String[] args) {
        setOrderNotProcessed("a923174d-f2ad-4618-a1ca-2db455d18744");
    }

    public static List getOrders() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase p refers to the object
        String strQuery = "SELECT o FROM Order o WHERE o.id IS NOT NULL";

        // Issue the query and all Products
        TypedQuery<Order> tq = em.createQuery(strQuery, Order.class);
        List<Order> orders = null;
        try {
            // Get matching product objects and output
            orders = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return orders;
    }

    public static Order getOrderByOrderId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT o FROM Order o WHERE o.id = :id";

        TypedQuery<Order> tq = em.createQuery(query, Order.class);
        tq.setParameter("id", id);

        Order order = null;
        try {
            order = tq.getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            //em.close();
        }
        return order;
    }

    public static void setOrderProcessed(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "UPDATE Order o SET o.orderStatusCode='60ff029d-f91f-4b89-a3bd-6e87538d45d6' WHERE o.id = :id";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Update executed");
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void setOrderNotProcessed(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "UPDATE Order o SET o.orderStatusCode='d32f2d6f-109b-480b-9454-34dd1334db27' WHERE o.id = :id";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Update executed");
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
