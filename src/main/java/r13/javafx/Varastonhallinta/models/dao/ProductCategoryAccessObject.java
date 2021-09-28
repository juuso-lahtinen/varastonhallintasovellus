package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.ProductCategory;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

public class ProductCategoryAccessObject {
    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    public static void main(String[] args) {
        //createProductCategory("Pyjamas", "For sleepless nights");
        //addProduct("Blue Pyjama", 19.99, "Warm", 10, "C8784D", "0943ee31-e715-476c-b4e6-a63a64adf16c");
    }

    public static void createProductCategory(String name, String description) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            ProductCategory category = new ProductCategory();
            category.setName(name);
            category.setDescription(description);

            em.persist(category);
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
