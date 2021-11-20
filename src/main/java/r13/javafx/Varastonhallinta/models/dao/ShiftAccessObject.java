package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.Shift;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class ShiftAccessObject {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");

    public static void main(String[] args) {
        LocalDate time = LocalDate.now();
        Shift shift = new Shift("16.00", "18.00", time);
        addShift(shift);
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
