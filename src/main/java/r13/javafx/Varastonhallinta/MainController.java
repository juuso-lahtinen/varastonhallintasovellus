package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.Shift;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ShiftAccessObject;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;


public class MainController implements Initializable {


    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;


    ResourceBundle bundle = Singleton.getInstance().getBundle();


    private ShiftAccessObject shiftDao = new ShiftAccessObject();
    private UserAccessObject userDao = new UserAccessObject();
    private List<TableColumn<User, String>> columns = new ArrayList<>();
    private String username = Singleton.getInstance().getUsername();
    final static int MAX_DAYS = 60;
    @FXML
    private TableView<User> shiftTable;
    @FXML
    private TableColumn<User, String> employeeCol;

    /***/

    private OrderAccessObject dao = new OrderAccessObject();
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> orderidCol;
    @FXML
    private TableColumn<Order, String> customerCol;
    @FXML
    private TableColumn<Order, String> dateCol;
    @FXML
    private TableColumn<Order, String> statusCol;

    @FXML
    private Text usernameField;


    @FXML
    private void home(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    private void orders(MouseEvent event) throws IOException {
        loadPage("orders");
        highlight(event);
    }

    @FXML
    private void products(MouseEvent event) throws IOException {
        highlight(event);
        loadPage("products");

    }

    @FXML
    private void admin(MouseEvent event) throws IOException {
        loadPage("shiftsAdmin");
        highlight(event);
    }

    @FXML
    private void shifts(MouseEvent event) throws IOException {
        loadPage("shifts");
        highlight(event);
    }

    @FXML
    private void logOut(MouseEvent event) throws IOException {
        Singleton.getInstance().setUsername(null);
        loadPage("loginscreen");
    }

    private void loadPage(String page) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(page + ".fxml"), bundle);
        bp.setCenter(root);
    }

    private void highlight(MouseEvent event) throws IOException {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillShiftTable();
        fillOrderTable();
        usernameField.setText(Singleton.getInstance().getUsername());
    }

    private void fillOrderTable() {

        orderidCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        customerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + cellData.getValue().getCustomer().getLastName()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderedAt().toString()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatusCode().getDescription()));
        FilteredList<Order> filteredData = new FilteredList(FXCollections.observableArrayList(dao.getOrders()), p -> true);
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());
        orderTable.setItems(sortedData);

    }


    private void fillShiftTable() {
        //Default date values
        LocalDate now = LocalDate.now();
        LocalDate weekFromNow = LocalDate.now().plusDays(MAX_DAYS);

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


    private void fetchShifts() {
        ObservableList<User> shifts = FXCollections.observableArrayList(userDao.getUser(username));
        shiftTable.setItems(shifts);

        shiftTable.getItems().forEach(u -> u.setShifts(shiftDao.getShiftsByUserId(u.getId())));
    }


}
