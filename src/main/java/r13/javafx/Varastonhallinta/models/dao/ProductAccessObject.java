package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.ProductCategory;
import javax.persistence.*;
import java.util.List;




public class ProductAccessObject {
	
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    public static void main(String[] args) {
        removeProduct("f6a05785-3e72-43c0-99af-0755881f524c");
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

        String strQuery = "SELECT p FROM Product p WHERE p.id IS NOT NULL";

        TypedQuery<Product> tq = em.createQuery(strQuery, Product.class);
        List<Product> products = null;
        try {
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

    public static Product addProduct(Product product) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(product);
            transaction.commit();
            return product;
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
    
    
    public static boolean removeProduct(String id) {
    	
    	
    	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
    	String query = "DELETE Product p WHERE p.id = :id";
    	
        try {

            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            System.out.println("query success");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
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
