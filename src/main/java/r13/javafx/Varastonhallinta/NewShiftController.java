package r13.javafx.Varastonhallinta;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import r13.javafx.Varastonhallinta.models.Shift;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.ShiftAccessObject;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class NewShiftController implements Initializable {


    private UserAccessObject userDao = new UserAccessObject();
    private ShiftAccessObject shiftDao = new ShiftAccessObject();

    @FXML
    private DatePicker shiftDate;

    @FXML
    private ComboBox<String> shiftEmployee;

    @FXML
    private ComboBox<String> shiftStart;

    @FXML
    private ComboBox<String> shiftEnd;

    @FXML
    private void addShift() {
        User userForShift = userDao.getDBUsername(shiftEmployee.getValue());
        String start = shiftStart.getValue();
        String end = shiftEnd.getValue();
        LocalDate date = shiftDate.getValue();

        Shift shiftToAdd = new Shift(userForShift, start, end, date);

        if (shiftDate.getValue() == null || shiftEmployee.getValue().isEmpty() || shiftStart.getValue().isEmpty() || shiftEnd.getValue().isEmpty()) {
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "Please fill all the required fields", ButtonType.OK);
                dialog.showAndWait();
            });
        } else if (shiftExists(shiftToAdd)) {
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "User is already working", ButtonType.OK);
                dialog.showAndWait();
            });
        } else {
            try {
                shiftDao.addShift(shiftToAdd);
                Platform.runLater(() -> {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Shift added", ButtonType.OK);
                    dialog.showAndWait();
                    clearFields();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTimeTabs();
        fillUserMenu();
    }

    private void fillUserMenu() {
        List<User> users = userDao.getUsers();
        users.forEach(u -> {
            shiftEmployee.getItems().add(u.getUsername());
        });
    }

    private void fillTimeTabs() {
        shiftStart.getItems().add("6.00");
        shiftStart.getItems().add("9.00");
        shiftStart.getItems().add("14.00");
        shiftStart.getItems().add("22.00");

        shiftEnd.getItems().add("6.00");
        shiftEnd.getItems().add("14.00");
        shiftEnd.getItems().add("17.00");
        shiftEnd.getItems().add("22.00");
    }

    private void clearFields() {
        shiftDate.getEditor().clear();
        shiftEmployee.getSelectionModel().clearSelection();
        shiftStart.getSelectionModel().clearSelection();
        shiftEnd.getSelectionModel().clearSelection();
    }

    // Super simple check if the user is working
    private boolean shiftExists(Shift shift) {
        List<Shift> shifts = shiftDao.getShifts();
        boolean exists = false;
        for (Shift s : shifts) {
            if (s.getUser().getUsername().equals(shift.getUser().getUsername()) && s.getDate().equals(shift.getDate()) && s.getStart().equals(shift.getStart())) {
                exists = true;
            }
        }
        return exists;
    }
}

