package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Product;

import r13.javafx.Varastonhallinta.models.ProductCategory;


import javax.persistence.*;
import java.util.List;




public class ProductAccessObject {
    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    public static void main(String[] args) {
        decreaseStock("4e0f2f6e-d461-4c7d-94fa-c02a1a49da5f", 2);
    }

    public static void decreaseStock(String id, int amount) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "UPDATE Product p SET p.stock = p.stock - :amount WHERE p.id = :id";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).setParameter("amount", amount).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Update executed");
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
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

    public static Product getProduct(String id) {
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
        return product;
    }

    public static boolean addProduct(Product product) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    
    public static boolean removeProduct(Product product) {
    	
    	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    	em.persist(product);
    	em.flush();
        em.clear();

        em.createNativeQuery("delete from Product where id = :id")
        .setParameter("id", product.getId())
        .executeUpdate();
        
        return true; //palauttaa nyt aina true

      //assertThat(em.find(Product.class, product.getId()), nullValue()); nullValue() ei toimi
    }
    


    public static Product editProduct(Product product) {


        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            Product editedProduct = getProduct(product.getId());
            em.detach(editedProduct);
            editedProduct.setName(product.getName());
            editedProduct.setPrice(product.getPrice());
            editedProduct.setDescription(product.getDescription());
            editedProduct.setStock(product.getStock());
            editedProduct.setLocation(product.getLocation());

            em.merge(editedProduct);
            transaction.commit();
            return editedProduct;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            em.close();
        }
        return null;
    }
}
