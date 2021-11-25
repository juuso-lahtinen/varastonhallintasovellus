package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    private OrderAccessObject dao = new OrderAccessObject();

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

    @FXML
    private void openOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("singleOrder.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Manage order");
        stage.setScene(new Scene(loader.load(), 800, 600));

        // Pass selected order
        SingleOrderController controller = loader.getController();
        controller.initData(orderTable.getSelectionModel().getSelectedItem());

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderidCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        customerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + cellData.getValue().getCustomer().getLastName()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderedAt().toString()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatusCode().getDescription()));

        // Add order filtering
        FilteredList<Order> filteredData = new FilteredList(FXCollections.observableArrayList(dao.getOrders()), p -> true);
        searchBar.textProperty().addListener((observable, oldVal, newVal) -> {
            filteredData.setPredicate(order -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newVal.toLowerCase();

                if (order.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getCustomer().getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getCustomer().getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getOrderedAt().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getOrderStatusCode().getDescription().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());
        orderTable.setItems(sortedData);
    }
}
