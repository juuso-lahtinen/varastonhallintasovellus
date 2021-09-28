package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.Product;

import javax.persistence.*;
import java.util.List;

public class OrderAccessObject {

    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    public static void main(String[] args) {
        List<Order> list = getOrders();
        list.forEach(e -> System.out.println(e.getOrderedAt()));
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
}
