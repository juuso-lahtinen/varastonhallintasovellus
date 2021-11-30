package r13.javafx.Varastonhallinta;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Shift;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.ShiftAccessObject;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShiftViewController implements Initializable {

    private ShiftAccessObject shiftDao = new ShiftAccessObject();
    private UserAccessObject userDao = new UserAccessObject();
    private List<TableColumn<User, String>> columns = new ArrayList<>();
    private String username = Singleton.getInstance().getUsername();
    final static int MAX_DAYS = 60;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker tillDate;

    @FXML
    private TableView<User> shiftTable;

    @FXML
    private TableColumn<User, String> employeeCol;

    @FXML
    void openNewShift(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newShift.fxml"));

        Stage stage = new Stage();
        stage.setTitle("New shift");
        stage.setScene(new Scene(loader.load()));
        // Pass controller if needed
        //SingleOrderViewController controller = loader.getController();
        //controller.initData(orderTable.getSelectionModel().getSelectedItem());
        stage.show();
    }

    
    @FXML
    void updateShifts(ActionEvent event) {
        LocalDate newStart = fromDate.getValue();
        LocalDate newTill = tillDate.getValue();

        // Check if there are too many days
        if (!tooManyDays(newStart, newTill)) {


            // Clear old values
            int dayCount = (int) ChronoUnit.DAYS.between(newStart, newTill);
            shiftTable.getItems().clear();
            shiftTable.getColumns().remove(1, shiftTable.getColumns().size());
            columns.clear();

            // Create new columns
            for (int i = 0; i < dayCount; i++) {
                columns.add(new TableColumn<>(newStart.plusDays(i).toString()));
            }
            shiftTable.getColumns().addAll(columns);

            // Fill cells
            employeeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
            for (TableColumn<User, String> tc : columns) {
                tc.setCellValueFactory(cellData -> {
                    Optional<Shift> shift = cellData.getValue().getShifts().stream().filter(s -> s.getDate().equals(LocalDate.parse(tc.getText()))).findAny();
                    if (shift.isPresent()) {
                        return new SimpleStringProperty(shift.get().getStart().toString() + "-" + shift.get().getEnd().toString());
                    } else {
                        return new SimpleStringProperty("");
                    }
                });
            }
            fetchShifts();
        } else {
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "Can not show over " + MAX_DAYS + " days", ButtonType.OK);
                dialog.setTitle("Too many days selected");
                dialog.showAndWait();
            });
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Default date values
        LocalDate now = LocalDate.now();
        LocalDate weekFromNow = LocalDate.now().plusDays(MAX_DAYS);
        fromDate.setValue(now);
        tillDate.setValue(weekFromNow);

        //Create columns
        int dayCount = (int) ChronoUnit.DAYS.between(now, weekFromNow);
        for (int i = 0; i < dayCount; i++) {
            columns.add(new TableColumn<>(now.plusDays(i).toString()));
        }
        shiftTable.getColumns().addAll(columns);

        // Fill cells
        employeeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        for (TableColumn<User, String> tc : columns) {
            tc.setCellValueFactory(cellData -> {
                Optional<Shift> shift = cellData.getValue().getShifts().stream().filter(s -> s.getDate().equals(LocalDate.parse(tc.getText()))).findAny();
                if (shift.isPresent()) {
                    return new SimpleStringProperty(shift.get().getStart().toString() + "-" + shift.get().getEnd().toString());
                } else {
                    return new SimpleStringProperty("");
                }
            });
        }

        fetchShifts();
        shiftTable.getSelectionModel().setCellSelectionEnabled(true);
    }

    private boolean tooManyDays(LocalDate startDate, LocalDate tillDate) {
        if (ChronoUnit.DAYS.between(startDate, tillDate) > MAX_DAYS) {
            return true;
        }
        return false;
    }

    private void fetchShifts() {
        ObservableList<User> shifts = FXCollections.observableArrayList(userDao.getUser(username));
        shiftTable.setItems(shifts);
        
        shiftTable.getItems().forEach(u -> u.setShifts(shiftDao.getShiftsByUserId(u.getId())));
    }
}