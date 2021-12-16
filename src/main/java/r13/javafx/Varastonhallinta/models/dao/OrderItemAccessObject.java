package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.OrderItem;

import javax.persistence.*;
import java.util.List;

public class OrderItemAccessObject {

    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    // Returns a list of OrderItems based on OrderId from the database
    public static List getOrderItemsByOrderId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT o FROM OrderItem o WHERE o.order.id = :id";

        TypedQuery<OrderItem> tq = em.createQuery(query, OrderItem.class);
        tq.setParameter("id", id);

        List<OrderItem> orderItems = null;
        try {
            orderItems = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return orderItems;
    }

    // Returns a list of OrderItems based on ProductId from the database
    public static List getOrderItemsByProductId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT o FROM OrderItem o WHERE o.product.id = :id";

        TypedQuery<OrderItem> tq = em.createQuery(query, OrderItem.class);
        tq.setParameter("id", id);

        List<OrderItem> orderItems = null;
        try {
            orderItems = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return orderItems;
    }
}
