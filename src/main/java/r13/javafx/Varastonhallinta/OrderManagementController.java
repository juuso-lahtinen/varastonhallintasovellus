package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;

import java.io.IOException;
import java.sql.Timestamp;

public class OrderManagementController {

    private OrderAccessObject dao = new OrderAccessObject();

    @FXML
    private Button backBtn;

    @FXML
    private Button orderDetailsBtn;

    @FXML
    private TableView<Order> table;

    @FXML
    private TableColumn<Order, String> orderNumber;

    @FXML
    private TableColumn<Order, Timestamp> orderDate;

    @FXML
    private TableColumn<Order, String> recipient;

    @FXML
    private TableColumn<Order, String> email;

    @FXML
    private TableColumn<Order, String> orderStatus;

    public void changeSceneToMainView(ActionEvent event) throws IOException {
        Parent orderManagementViewParent = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        Scene mainViewScene = new Scene(orderManagementViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainViewScene);
        window.show();
    }

    public void initialize() {
        orderNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("orderedAt"));
        recipient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + cellData.getValue().getCustomer().getLastName()));
        email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getEmail()));
        orderStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatusCode().getDescription()));

        table.setItems(getOrders());
    }

    private ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList(dao.getOrders());
        return orders;
    }

    public void changeSceneToOrderDetailsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("singleOrder.fxml"));
        Parent orderViewParent = loader.load();

        Scene singleOrderViewScene = new Scene(orderViewParent);

        SingleOrderController controller = loader.getController();
        controller.initData(table.getSelectionModel().getSelectedItem());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(singleOrderViewScene);
        window.show();
    }
}
