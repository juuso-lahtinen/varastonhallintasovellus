package r13.javafx.Varastonhallinta.models.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import r13.javafx.Varastonhallinta.models.User;


public class UserAccessObject {
	
	// Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");
    
    public static void main(String[] args) {      
    	//testi
        //System.out.println(checkLogin("123", "juuso")); 
    }
    
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
}