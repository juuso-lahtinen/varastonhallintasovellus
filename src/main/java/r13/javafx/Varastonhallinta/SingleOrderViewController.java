package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.OrderItem;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;
import r13.javafx.Varastonhallinta.models.dao.OrderItemAccessObject;

public class SingleOrderViewController {

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
    private Label orderTotal;

    @FXML
    private Label orderTax;

    @FXML
    private Label orderShippingFee;

    @FXML
    private Label orderSubtotal;

    private Order selectedOrder = null;

    private OrderItemAccessObject dao = new OrderItemAccessObject();
    private OrderAccessObject orderDao = new OrderAccessObject();

    public void initData(Order order) {
        this.selectedOrder = order;
        orderId.setText(selectedOrder.getId());
        customerName.setText(selectedOrder.getCustomer().getFirstName() + " " + selectedOrder.getCustomer().getLastName());
        customerEmail.setText(selectedOrder.getCustomer().getEmail());
        customerPhone.setText(selectedOrder.getCustomer().getPhone());
        populateItemsTable();
        setTotalValues();
    }

    private void populateItemsTable() {
        orderItemProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        orderItemPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject());
        orderItemQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        orderItemSubtotal.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        orderItemsTable.setItems(FXCollections.observableArrayList(dao.getOrderItemsByOrderId(selectedOrder.getId())));
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
}
