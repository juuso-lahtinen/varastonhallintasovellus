package r13.javafx.Varastonhallinta;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import r13.javafx.Varastonhallinta.models.Shift;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.ShiftAccessObject;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class NewShiftController implements Initializable {

	ResourceBundle bundle = Singleton.getInstance().getBundle();	
    private UserAccessObject userDao = new UserAccessObject();
    private ShiftAccessObject shiftDao = new ShiftAccessObject();


    @FXML
    private DatePicker shiftDate;

    @FXML
    private ComboBox<String> shiftEmployee;

    @FXML
    private ComboBox<LocalTime> shiftStart;

    @FXML
    private ComboBox<LocalTime> shiftEnd;

    @FXML
    private Button shiftStartCustom;

    @FXML
    private TextField customStart;

    @FXML
    private Button shiftStartCancel;

    @FXML
    private Button shiftEndCustom;

    @FXML
    private TextField customEnd;

    @FXML
    private Button shiftEndCancel;

    @FXML
    private Button addShiftBtn;

    @FXML
    void cancelCustomEnd(ActionEvent event) {
        shiftEnd.setVisible(true);
        customEnd.setVisible(false);
        shiftEndCancel.setVisible(false);
        shiftEndCustom.setVisible(true);
        shiftEndCustom.toBack();
        shiftEnd.toBack();
    }

    @FXML
    void cancelCustomStart(ActionEvent event) {
        shiftStart.setVisible(true);
        customStart.setVisible(false);
        shiftStartCancel.setVisible(false);
        shiftStartCustom.setVisible(true);
        shiftStartCustom.toBack();
        shiftStart.toBack();
    }

    @FXML
    void setCustomEnd(ActionEvent event) {
        shiftEnd.setVisible(false);
        customEnd.setVisible(true);
        shiftEndCancel.setVisible(true);
        shiftEndCustom.setVisible(false);
        shiftEndCancel.toBack();
        customEnd.toBack();
    }

    @FXML
    void setCustomStart(ActionEvent event) {
        shiftStart.setVisible(false);
        customStart.setVisible(true);
        shiftStartCancel.setVisible(true);
        shiftStartCustom.setVisible(false);
        shiftStartCancel.toBack();
        customStart.toBack();

    }

    @FXML
    void addShift(ActionEvent event) {
        try {
            User userForShift = userDao.getDBUsername(shiftEmployee.getValue());
            LocalDate date = shiftDate.getValue();
            LocalTime start = shiftStart.isVisible() ? shiftStart.getValue() : LocalTime.parse(customStart.getText());
            LocalTime end = shiftEnd.isVisible() ? shiftEnd.getValue() : LocalTime.parse(customEnd.getText());
            Shift shiftToAdd = new Shift(userForShift, start, end, date);

            if (shiftDate.getValue() == null || shiftEmployee.getValue().isEmpty() || (shiftStart.isVisible() && shiftStart.getValue() == null) ||
                    (shiftEnd.isVisible() && shiftEnd.getValue() == null) || (customStart.isVisible() && customStart.getText().isEmpty()) ||
                    (customEnd.isVisible() && customEnd.getText().isEmpty())) {
                Platform.runLater(() -> {
                    Alert dialog = new Alert(Alert.AlertType.ERROR, bundle.getString("fillAll"), ButtonType.OK);
                    dialog.showAndWait();
                });
            } else if (alreadyWorking(userForShift, shiftToAdd)) {
                Platform.runLater(() -> {
                    Alert dialog = new Alert(Alert.AlertType.ERROR, bundle.getString("alreadyWork"), ButtonType.OK);
                    dialog.showAndWait();
                });
            } else {
                try {
                    shiftDao.addShift(shiftToAdd);
                    Platform.runLater(() -> {
                        Alert dialog = new Alert(Alert.AlertType.INFORMATION, bundle.getString("shiftAdded"), ButtonType.OK);
                        dialog.showAndWait();
                        clearFields();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, bundle.getString("customTimeAlert"), ButtonType.OK);
                dialog.showAndWait();
            });
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTimeTabs();
        fillUserMenu();
        customInitials();
    }

    private void customInitials() {
        customStart.setVisible(false);
        customEnd.setVisible(false);
        shiftStartCancel.setVisible(false);
        shiftEndCancel.setVisible(false);

    }

    private void fillUserMenu() {
        List<User> users = userDao.getUsers();
        users.forEach(u -> {
            shiftEmployee.getItems().add(u.getUsername());
        });
    }

    private void fillTimeTabs() {
        shiftStart.getItems().add(LocalTime.parse("06:00:00"));
        shiftStart.getItems().add(LocalTime.parse("09:00:00"));
        shiftStart.getItems().add(LocalTime.parse("14:00:00"));
        shiftStart.getItems().add(LocalTime.parse("22:00:00"));

        shiftEnd.getItems().add(LocalTime.parse("06:00:00"));
        shiftEnd.getItems().add(LocalTime.parse("14:00:00"));
        shiftEnd.getItems().add(LocalTime.parse("17:00:00"));
        shiftEnd.getItems().add(LocalTime.parse("22:00:00"));
    }

    private void clearFields() {
        shiftDate.getEditor().clear();
        shiftEmployee.getSelectionModel().clearSelection();
        shiftStart.getSelectionModel().clearSelection();
        shiftEnd.getSelectionModel().clearSelection();
    }

    // Super simple check if the user is working
    private boolean alreadyWorking(User user, Shift shift) {
        boolean isWorking = false;
        List<Shift> userShifts = shiftDao.getShiftsByUserId(user.getId());
        for (Shift s : userShifts) {
            if (s.getDate().equals(shift.getDate())) {
                isWorking = true;
            }
        }
        return isWorking;
    }
}

