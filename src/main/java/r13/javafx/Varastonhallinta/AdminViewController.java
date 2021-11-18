package r13.javafx.Varastonhallinta;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    private TableView<?> openShiftTable;

    @FXML
    private TableColumn<?, ?> openShiftDate;

    @FXML
    private TableColumn<?, ?> openShiftStart;

    @FXML
    private TableColumn<?, ?> openShiftEnd;

    @FXML
    private TableView<?> upcomingShiftTable;

    @FXML
    private TableColumn<?, ?> upcomingShiftEmployee;

    @FXML
    private TableColumn<?, ?> upcomingShiftDate;

    @FXML
    private TableColumn<?, ?> upcomingShiftStart;

    @FXML
    private TableColumn<?, ?> upcomingShiftEnd;

    @FXML
    private DatePicker newShiftDate;

    @FXML
    private ComboBox newShiftStart;

    @FXML
    private ComboBox newShiftEnd;

    @FXML
    private ComboBox newShiftEmployeeId;

    @FXML
    private Button newShiftAdd;

    private UserAccessObject userDao = new UserAccessObject();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUserIdMenu();
        fillTimeTabs();
    }

    public void fillUserIdMenu() {
        List<User> users = userDao.getUsers();
        users.forEach(u -> {
            newShiftEmployeeId.getItems().add(u.getId());
        });
    }

    public void fillTimeTabs() {
        newShiftStart.getItems().add("6.00");
        newShiftStart.getItems().add("9.00");
        newShiftStart.getItems().add("14.00");
        newShiftStart.getItems().add("22.00");

        newShiftEnd.getItems().add("6.00");
        newShiftEnd.getItems().add("14.00");
        newShiftEnd.getItems().add("17.00");
        newShiftEnd.getItems().add("22.00");
    }
}
