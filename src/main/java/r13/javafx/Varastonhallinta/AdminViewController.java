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
import r13.javafx.Varastonhallinta.models.dao.ShiftAccessObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    private ShiftAccessObject shiftDao = new ShiftAccessObject();

    private List<TableColumn<Shift, String>> columns = new ArrayList<>();

    @FXML
    private Button newShiftBtn;

    @FXML
    private Button delShift;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker tillDate;

    @FXML
    private TableView<Shift> shiftTable;

    @FXML
    private TableColumn<Shift, String> employeeCol;

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
    void deleteShift(ActionEvent event) {
        try {
            Shift shiftToDelete = shiftTable.getSelectionModel().getSelectedItem();
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure you want to delete this shift?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                shiftDao.deleteShiftById(shiftToDelete.getId());
                shiftTable.getItems().remove(shiftToDelete);

            } else {
                confirmation.close();
            }
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert dialog = new Alert(Alert.AlertType.ERROR, "Select a shift to delete", ButtonType.OK);
                dialog.setTitle("Error");
                dialog.showAndWait();
            });
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Default date values
        LocalDate now = LocalDate.now();
        LocalDate weekFromNow = LocalDate.now().plusDays(7);
        fromDate.setValue(now);
        tillDate.setValue(weekFromNow);

        //Create columns
        int daycount = (int) ChronoUnit.DAYS.between(now, weekFromNow);
        for (int i = 0; i < daycount; i++) {
            columns.add(new TableColumn<Shift, String>(LocalDate.now().plusDays(i).toString()));
            //shiftTable.getColumns().add(new TableColumn<Shift, String>(LocalDate.now().plusDays(i).toString()));
        }
        shiftTable.getColumns().addAll(columns);

        // Fill cells
        employeeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getFirstName() + " " + cellData.getValue().getUser().getLastName()));
        for (TableColumn<Shift, String> tc : columns) {
            tc.setCellValueFactory(cellData -> {
                String cellVal = tc.getText().equals(cellData.getValue().getDate().toString()) ? cellData.getValue().getStart() + "-" + cellData.getValue().getEnd() : "";

                return new SimpleStringProperty(cellVal);
            });
        }

        ObservableList<Shift> shifts = FXCollections.observableArrayList(shiftDao.getShifts());

        shiftTable.setItems(shifts);
    }

    private boolean updateDays(LocalDate startDate, LocalDate tillDate) {
        if (ChronoUnit.DAYS.between(startDate, tillDate) > 14) {
            return false;
        }
        return true;
    }
}
