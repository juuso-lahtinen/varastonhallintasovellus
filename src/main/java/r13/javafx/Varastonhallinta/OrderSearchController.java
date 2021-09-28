package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;

import java.io.IOException;
import java.sql.Timestamp;

public class OrderSearchController {

    private OrderAccessObject dao = new OrderAccessObject();

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Order> table;

    @FXML
    private TableColumn<Order, String> orderNumber;

    @FXML
    private TableColumn<Order, Timestamp> orderDate;

    @FXML
    private TableColumn<Order, String> orderStatus;

    @FXML
    private void switchToMainWindow() throws IOException {
        App.setRoot("mainwindow");
    }

    public void initialize() {
        orderNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("orderedAt"));
        orderStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatusCode().getDescription()));

        table.setItems(getOrders());
    }

    private ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList(dao.getOrders());
        return orders;
    }
}
