package DataAccessObjectTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;


public class UserAccessObjectTest {

    private UserAccessObject userDAO = new UserAccessObject();


    private User user;


    @BeforeEach
    public void beforeEach() {
        user = new User("esimerkkiEero", "salasana", "Eero", "Esimerkki");
    }


    @Test
    @Order(1)
    @DisplayName("Test for adding a user")
    public void testAdd() {
        User newUser = userDAO.addUser(user);

        boolean userState = false;
        if (newUser != null)
            userState = true;
        assertTrue(userState, "addUser() doesn't work");
        assertTrue((user = (User) userDAO.getDBUsername(newUser.getUsername())) != null, "getDBUsername doesn't work");

        assertTrue((user = (User) userDAO.getDBUsername(newUser.getUsername())) != null, "getDBUsername doesn't work");

        userDAO.removeUserByUsername(newUser.getUsername());
    }

    @Test
    @Order(2)
    @DisplayName("Test for logging in")
    public void testLogin() {
        User newUser = userDAO.addUser(user);

        boolean userState = false;
        if (newUser != null)
            userState = true;
        assertTrue(userState, "addUser() doesn't work");
        assertTrue((user = (User) userDAO.getDBUsername(newUser.getUsername())) != null, "getDBUsername doesn't work");

        assertTrue(userDAO.checkLogin(user.getUsername(), user.getPassword()), "getDBUsername doesn't work");

        userDAO.removeUserByUsername(newUser.getUsername());
    }


}
