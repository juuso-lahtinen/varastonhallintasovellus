package r13.javafx.Varastonhallinta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    private Button newShiftBtn;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker tillDate;

    @FXML
    private TableView<?> shiftTable;

    @FXML
    private TableColumn<?, ?> employeeCol;

    @FXML
    private TableColumn<?, ?> mondayCol;

    @FXML
    private TableColumn<?, ?> tuesdayCol;

    @FXML
    private TableColumn<?, ?> wednesdayCol;

    @FXML
    private TableColumn<?, ?> thursdayCol;

    @FXML
    private TableColumn<?, ?> fridayCol;

    @FXML
    private TableColumn<?, ?> saturdayCol;

    @FXML
    private TableColumn<?, ?> sundayCol;

    @FXML
    private void openNewShift() throws IOException {
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

    }
}
