package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.Shift;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class ShiftAccessObject {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");

    public static void main(String[] args) {
        System.out.println(getShiftsByUserId("54a0e234-c980-4be6-a737-adcc137d132a"));

    }

    public static Shift addShift(Shift shift) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(shift);
            transaction.commit();
            return shift;
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

    public static boolean deleteShiftById(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "DELETE Shift s WHERE s.id = :id";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public static List getShiftsByUserId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT s FROM Shift s WHERE s.user.id = :id";

        TypedQuery<Shift> tq = em.createQuery(query, Shift.class);
        tq.setParameter("id", id);
        List<Shift> shifts = null;

        try {
            shifts = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return shifts;
    }

    public static List getShifts() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase p refers to the object
        String strQuery = "SELECT s FROM Shift s WHERE s.id IS NOT NULL";

        TypedQuery<Shift> tq = em.createQuery(strQuery, Shift.class);
        List<Shift> shifts = null;
        try {
            // Get matching product objects and output
            shifts = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return shifts;
    }
}
