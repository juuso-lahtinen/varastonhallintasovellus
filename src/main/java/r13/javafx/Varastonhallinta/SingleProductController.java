package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

public class SingleProductController {

    private Product selectedProduct;

    private ProductAccessObject dao = new ProductAccessObject();
    private OrderAccessObject orderDao = new OrderAccessObject();

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> orderId;

    @FXML
    private TableColumn<Order, String> orderStatus;

    @FXML
    private TableColumn<Order, Integer> orderQuantity;

    @FXML
    private TableColumn<Order, Integer> orderedAt;


    @FXML
    private Button productTabBackBtn;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private Button generalTabBackBtn;



    private void initializeProducts() {
        //todo
    }

    private void initializeDetails() {
        idLabel.setText(selectedProduct.getId());
        nameLabel.setText(selectedProduct.getName());
        descriptionLabel.setText(selectedProduct.getDescription());
        locationLabel.setText(selectedProduct.getLocation());
        priceLabel.setText(Double.toString(selectedProduct.getPrice()));
        stockLabel.setText(Integer.toString(selectedProduct.getStock()));
    }


    public void initData(Product product) {
        selectedProduct = product;
        initializeDetails();

    }

    @FXML
    private void switchToProductManagementWindow(ActionEvent event) throws IOException {
    	Parent mainViewParent = FXMLLoader.load(getClass().getResource("productManagement.fxml"));
        Scene newProductViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newProductViewScene);
        window.show();
    }
	
	
	
}
