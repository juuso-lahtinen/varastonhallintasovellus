package r13.javafx.Varastonhallinta;

import javax.persistence.*;
import java.util.List;

public class DataAccessObject {
    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");

    public static void main(String[] args) {
        getCustomers();
        ENTITY_MANAGER_FACTORY.close();
    }

    public static void getCustomers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase p refers to the object
        String strQuery = "SELECT p FROM Product p WHERE p.id IS NOT NULL";

        // Issue the query and all Products
        TypedQuery<Product> tq = em.createQuery(strQuery, Product.class);
        List<Product> products;
        try {
            // Get matching product objects and output
            products = tq.getResultList();
            products.forEach(product -> System.out.println(product.getId() + ": " + product.getName()));
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
