package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.OrderItem;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;
import r13.javafx.Varastonhallinta.models.dao.OrderItemAccessObject;

import java.io.IOException;
import java.util.List;

public class SingleOrderController {

	private Order selectedOrder;

	private OrderItemAccessObject dao = new OrderItemAccessObject();
	private OrderAccessObject orderDao = new OrderAccessObject();

	/* ************************* Product view ************************* */
	@FXML
	private Button backBtn;

	@FXML
	private TableView<OrderItem> productTable;

	@FXML
	private TableColumn<OrderItem, String> productId;

	@FXML
	private TableColumn<OrderItem, String> productName;

	@FXML
	private TableColumn<OrderItem, Integer> productQuantity;

	@FXML
	private TableColumn<OrderItem, Integer> productPicking;

	@FXML
	private TableColumn<OrderItem, Integer> productStock;

	@FXML
	private TableColumn<OrderItem, Double> productTotalPrice;

	@FXML
	private TableColumn<OrderItem, String> productLocation;

	/* ************************* Customer view ************************* */
	@FXML
	private Button customerTabBackBtn;

	@FXML
	private Label customerIdLabel;

	@FXML
	private Label firstNameLabel;

	@FXML
	private Label lastNameLabel;

	@FXML
	private Label emailLabel;

	@FXML
	private Label phoneLabel;

	@FXML
	private Label registerDateLabel;

	/* ************************* General view ************************* */

	@FXML
	private Button generalTabBackBtn;

	@FXML
	private Button processOrderBtn;

	@FXML
	private CheckBox processCheckBox;

	@FXML
	private Label orderId;

	@FXML
	private Label totalProducts;

	@FXML
	private Label totalPrice;

	private void initializeProducts() {
		productId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getId()));
		productName
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
		productQuantity.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
		productStock.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getStock()).asObject());
		productTotalPrice
				.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		productLocation.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getLocation()));
		productPicking.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		productTable.setItems(getOrderItems());
		productTable.setEditable(true);
		productTable.refresh();
	}

	private void initializeCustomer() {
		customerIdLabel.setText(selectedOrder.getCustomer().getId());
		firstNameLabel.setText(selectedOrder.getCustomer().getFirstName());
		lastNameLabel.setText(selectedOrder.getCustomer().getLastName());
		emailLabel.setText(selectedOrder.getCustomer().getEmail());
		phoneLabel.setText(selectedOrder.getCustomer().getPhone());
		registerDateLabel.setText(selectedOrder.getCustomer().getRegisteredAt().toString());
	}

	private void initializeGeneral() {
		orderId.setText(selectedOrder.getId());
		totalProducts.setText(Integer.toString(orderDao.getOrderByOrderId(selectedOrder.getId()).getOrderItems()
				.stream().mapToInt(s -> s.getQuantity()).sum()));
		totalPrice.setText(Double.toString(orderDao.getOrderByOrderId(selectedOrder.getId()).getOrderItems().stream()
				.mapToDouble(s -> s.getPrice()).sum()) + " €");
	}

	private ObservableList<OrderItem> getOrderItems() {
		ObservableList<OrderItem> orderItems = FXCollections
				.observableArrayList(dao.getOrderItemsByOrderId(selectedOrder.getId()));

		return orderItems;
	}

	// Get Order object and initialize the view
	public void initData(Order order) {
		selectedOrder = order;
		initializeGeneral();
		initializeCustomer();
		initializeProducts();
	}

	public void changeSceneToOrderView(ActionEvent event) throws IOException {
		Parent orderManagementViewParent = FXMLLoader.load(getClass().getResource("orderManagement.fxml"));
		Scene orderViewScene = new Scene(orderManagementViewParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(orderViewScene);
		window.show();
	}
}
