package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private TextField searchBar;

    @FXML
    private Button openOrder;

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

    private OrderAccessObject dao = new OrderAccessObject();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Order> orderList = FXCollections.observableArrayList(dao.getOrders());
        orderTable.setItems(orderList);

        orderidCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        customerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + cellData.getValue().getCustomer().getLastName()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderedAt().toString()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatusCode().getDescription()));
    }
}
