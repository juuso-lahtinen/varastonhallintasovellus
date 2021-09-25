package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {
    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    public static List getProducts() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase p refers to the object
        String strQuery = "SELECT p FROM Product p WHERE p.id IS NOT NULL";

        // Issue the query and all Products
        TypedQuery<Product> tq = em.createQuery(strQuery, Product.class);
        List<Product> products = null;
        try {
            // Get matching product objects and output
            products = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return products;
    }

    public static void getProduct(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT p FROM Product p WHERE p.id = :id";

        TypedQuery<Product> tq = em.createQuery(query, Product.class);
        tq.setParameter("id", id);

        Product product = null;
        try {
            product = tq.getSingleResult();
            System.out.println(product.getId() + ": " + product.getName());
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void addProduct(String name, double price, String description, int stock, String location) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setStock(stock);
            product.setLocation(location);

            em.persist(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}