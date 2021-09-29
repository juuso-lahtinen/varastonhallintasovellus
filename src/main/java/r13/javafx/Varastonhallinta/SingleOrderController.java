package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.OrderItem;

import java.io.IOException;
import java.util.List;

public class SingleOrderController {

    private Order selectedOrder;

    @FXML
    private Button backBtn;

    @FXML
    private Tab generalTab;

    @FXML
    private Tab recipientTab;

    @FXML
    private Tab productTab;

    @FXML
    private TableView<OrderItem> productTable;

    @FXML
    private TableColumn<OrderItem, String> productId;

    @FXML
    private TableColumn<OrderItem, String> productName;

    @FXML
    private TableColumn<OrderItem, Integer> productQuantity;

    @FXML
    private TableColumn<OrderItem, Integer> productStock;

    @FXML
    private TableColumn<OrderItem, Double> productTotalPrice;

    @FXML
    private TableColumn<OrderItem, String> productLocation;

    @FXML
    private void switchToMainWindow() throws IOException {
        App.setRoot("orderManagement");
    }

    public void initialize() {
        productId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getId()));

        productTable.setItems(FXCollections.observableArrayList(selectedOrder.getOrderItems()));
        //productTable.setItems(FXCollections.observableArrayList(selectedOrder.getOrderItems()));
    }


    // Get Order object and initialize the view
    public void initData(Order order) {
        this.selectedOrder = order;

    }
}
