package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import r13.javafx.Varastonhallinta.models.OrderItem;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.dao.OrderItemAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

public class SingleProductController {

    private Product selectedProduct;

    private ProductAccessObject dao = new ProductAccessObject();
    private OrderItemAccessObject orderItemDao = new OrderItemAccessObject();

    @FXML
    private Button backBtn;
    
    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;
    
    @FXML
    private Button editBtn;
    
    @FXML
    private TableView<OrderItem> orderItemTable;

    @FXML
    private TableColumn<OrderItem, String> orderId;

    @FXML
    private TableColumn<OrderItem, Integer> quantity;

    @FXML
    private TableColumn<OrderItem, Double> price;


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
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField descriptionTextField;
    
    @FXML
    private TextField locationTextField;
    
    @FXML
    private TextField priceTextField;
    
    @FXML
    private TextField stockTextField;

    public void initializeTable() {
        orderId.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("id"));
        quantity.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<OrderItem, Double>("price"));
        
        FilteredList<OrderItem> filteredData = new FilteredList<>(getOrderItems(), p -> true);
        
		SortedList<OrderItem> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(orderItemTable.comparatorProperty());

		orderItemTable.setItems(filteredData);
    }

    private ObservableList<OrderItem> getOrderItems() {
    	if(selectedProduct != null) {
	        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList(orderItemDao.getOrderItemsByProductId(selectedProduct.getId()));
	        return orderItems;
    	}
    	else	{
    		return null;
    	}
    }

    private void initializeDetails() {
    	
    	saveBtn.setVisible(false);
    	cancelBtn.setVisible(false);
    	editBtn.setVisible(true);
    	
    	nameTextField.setVisible(false);
    	descriptionTextField.setVisible(false);
    	locationTextField.setVisible(false);
    	priceTextField.setVisible(false);
    	stockTextField.setVisible(false);
    	
    	if(selectedProduct != null) {
    		idLabel.setText(selectedProduct.getId());
            nameLabel.setText(selectedProduct.getName());
            descriptionLabel.setText(selectedProduct.getDescription());
            locationLabel.setText(selectedProduct.getLocation());
            priceLabel.setText(Double.toString(selectedProduct.getPrice()));
            stockLabel.setText(Integer.toString(selectedProduct.getStock()));
    	}
    	
        
    }
    
    @FXML
    public void startEdit()	{
    	if(selectedProduct != null) {
    		saveBtn.setVisible(true);
        	cancelBtn.setVisible(true);
        	editBtn.setVisible(false);
        	

    		nameTextField.setVisible(true);
        	descriptionTextField.setVisible(true);
        	locationTextField.setVisible(true);
        	priceTextField.setVisible(true);
        	stockTextField.setVisible(true);
        	
        	nameTextField.setText(selectedProduct.getName());
        	descriptionTextField.setText(selectedProduct.getDescription());
        	locationTextField.setText(selectedProduct.getLocation());
        	priceTextField.setText(Double.toString(selectedProduct.getPrice()));
        	stockTextField.setText(Integer.toString(selectedProduct.getStock()));
    	}
    }

    
    public void saveEdit()	{
    	
    	Product product = new Product(selectedProduct.getId(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), descriptionTextField.getText(), Integer.parseInt(stockTextField.getText()), locationTextField.getText());
    	Product editedProduct = dao.editProduct(product);
    	nameTextField.setVisible(false);
    	descriptionTextField.setVisible(false);
    	locationTextField.setVisible(false);
    	priceTextField.setVisible(false);
    	stockTextField.setVisible(false);
    	initData(dao.getProduct(selectedProduct.getId()));
    }
    
    public void cancelEdit()	{

    	nameTextField.setVisible(false);
    	descriptionTextField.setVisible(false);
    	locationTextField.setVisible(false);
    	priceTextField.setVisible(false);
    	stockTextField.setVisible(false);
    	saveBtn.setVisible(false);
    	cancelBtn.setVisible(false);
    	editBtn.setVisible(true);

    }
    

    public void initData(Product product) {
        this.selectedProduct = product;
        initializeDetails();
        initializeTable();

    }

    @FXML
    private void switchToProductManagementWindow(ActionEvent event) throws IOException {
    	Parent mainViewParent = FXMLLoader.load(getClass().getResource("products.fxml"));
        Scene newProductViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newProductViewScene);
        window.show();
    }

}
