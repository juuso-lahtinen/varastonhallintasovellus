package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.OrderItem;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;
import r13.javafx.Varastonhallinta.models.dao.OrderItemAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleOrderController {

    @FXML
    private Label orderId;

    @FXML
    private Label customerName;

    @FXML
    private Label customerEmail;

    @FXML
    private Label customerPhone;

    @FXML
    private Label customerAddress;

    @FXML
    private Label customerCity;

    @FXML
    private Label customerPostal;

    @FXML
    private Label orderStatus;

    @FXML
    private Button processBtn;

    @FXML
    private TableView<OrderItem> orderItemsTable;

    @FXML
    private TableColumn<OrderItem, String> orderItemProduct;

    @FXML
    private TableColumn<OrderItem, Double> orderItemPrice;

    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantity;

    @FXML
    private TableColumn<OrderItem, Double> orderItemSubtotal;

    @FXML
    private TableColumn<OrderItem, Integer> orderItemStock;

    @FXML
    private Label orderTotal;

    @FXML
    private Label orderTax;

    @FXML
    private Label orderShippingFee;

    @FXML
    private Label orderSubtotal;

    @FXML
    void processOrder(ActionEvent event) {
        Order order = orderDao.getOrderByOrderId(selectedOrder.getId());
        AtomicBoolean invalidStock = new AtomicBoolean(false);

        if (!alreadyProcessed(order)) {
            orderItemsTable.getItems().stream().forEach(item -> {
                if (item.getQuantity() > item.getProduct().getStock()) {
                    invalidStock.set(true);
                }
            });

            if (!invalidStock.get()) {
                orderItemsTable.getItems().stream().forEach(item -> {
                    productDao.decreaseStock(item.getProduct().getId(), item.getQuantity());
                });
                orderDao.setOrderProcessed(order.getId());
                orderStatus.setText(order.getOrderStatusCode().getDescription());
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Order processed", ButtonType.OK);
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Invalid stock", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Order already processed", ButtonType.OK);
            a.showAndWait();

        }
    }

    private Order selectedOrder = null;

    private OrderItemAccessObject dao = new OrderItemAccessObject();
    private OrderAccessObject orderDao = new OrderAccessObject();
    private ProductAccessObject productDao = new ProductAccessObject();

    public void initData(Order order) {
        this.selectedOrder = order;
        orderId.setText(selectedOrder.getId());
        customerName.setText(selectedOrder.getCustomer().getFirstName() + " " + selectedOrder.getCustomer().getLastName());
        customerEmail.setText(selectedOrder.getCustomer().getEmail());
        customerPhone.setText(selectedOrder.getCustomer().getPhone());
        customerAddress.setText(selectedOrder.getCustomer().getAddress().getAddress());
        customerCity.setText(selectedOrder.getCustomer().getAddress().getCity());
        customerPostal.setText(selectedOrder.getCustomer().getAddress().getPostal());
        orderStatus.setText(selectedOrder.getOrderStatusCode().getDescription());
        populateItemsTable();
        setTotalValues();
    }

    private void populateItemsTable() {
        orderItemProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        orderItemPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject());
        orderItemQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        orderItemSubtotal.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        orderItemStock.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getStock()).asObject());

        orderItemsTable.setItems(FXCollections.observableArrayList(dao.getOrderItemsByOrderId(selectedOrder.getId())));

        // Update bg-color
        orderItemsTable.setRowFactory(row -> new TableRow<>() {
            @Override
            protected void updateItem(OrderItem item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getProduct() == null) {
                    setStyle("");
                } else if (item.getProduct().getStock() < item.getQuantity()) {
                    setStyle("-fx-background-color: red;");
                } else {
                    setStyle("-fx-background-color: green;");
                }
            }
        });
    }

    private void setTotalValues() {
        double subTotal = orderDao.getOrderByOrderId(selectedOrder.getId()).getOrderItems().stream().mapToDouble(i -> i.getProduct().getPrice()).sum();
        double tax = Math.round(((subTotal * 0.10) * 100.0) / 100.0);
        double shipping = 0.0;
        double total = subTotal - tax - shipping;
        orderTax.setText(Double.toString(tax) + "€");
        orderShippingFee.setText(Double.toString(shipping) + "€");
        orderSubtotal.setText(Double.toString(subTotal) + "€");
        orderTotal.setText(Double.toString(total) + "€");
    }

    private Boolean alreadyProcessed(Order o) {
        return o.getOrderStatusCode().getDescription().equals("Order has been processed") ? true : false;
    }
}
