package r13.javafx.Varastonhallinta;

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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    private ShiftAccessObject shiftDao = new ShiftAccessObject();

    @FXML
    private Button newShiftBtn;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker tillDate;

    @FXML
    private TableView<Shift> shiftTable;

    @FXML
    private TableColumn<Shift, String> employeeCol;

    @FXML
    private TableColumn<Shift, String> dateCol;

    @FXML
    private TableColumn<Shift, String> startCol;

    @FXML
    private TableColumn<Shift, String> endCol;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getFirstName() + " " + cellData.getValue().getUser().getLastName()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        startCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart()));
        endCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd()));

        ObservableList<Shift> shifts = FXCollections.observableArrayList(shiftDao.getShifts());

        shiftTable.setItems(shifts);
    }
}
