package r13.javafx.Varastonhallinta;

import javafx.application.Platform;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductController  {

    private ProductAccessObject dao = new ProductAccessObject();

    @FXML
    private Button addBtn;
    
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button refreshBtn;
    
    @FXML
    private Button newProductButton;
    
    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> tableId;

    @FXML
    private TableColumn<Product, String> tableName;

    @FXML
    private TableColumn<Product, Double> tablePrice;

    @FXML
    private TableColumn<Product, String> tableDescription;

    @FXML
    private TableColumn<Product, Integer> tableStock;

    @FXML
    private TableColumn<Product, String> tableLocation;

    @FXML
    private TableColumn<Product, String> tableCategory;

    @FXML
    private TextField filterField;
    

    
    
    @FXML
    public void initialize() {
        tableId.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tableDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        tableStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        tableLocation.setCellValueFactory(new PropertyValueFactory<Product, String>("location")); 
        productTable.setPlaceholder(new Label("No products found"));
        
        FilteredList<Product> filteredData = new FilteredList<>(getProducts(), p -> true);
        
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(product -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (product.getLocation().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		SortedList<Product> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(productTable.comparatorProperty());

		productTable.setItems(sortedData);
    }

    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(dao.getProducts());
        return products;
    }
    
    
    
    
    
    
    @FXML
    private void deleteProduct()	{
    	
    	Product p = productTable.getSelectionModel().getSelectedItem();
    	
	    if(p != null)	{
	    	if(dao.removeProduct(p.getId()))	{
	    		Platform.runLater(() -> {
	    	        Alert dialog = new Alert(AlertType.INFORMATION, "Product removed.", ButtonType.OK);
	    	        dialog.showAndWait();
	    	    });
	    	} else	{
	    		Platform.runLater(() -> {
	    	        Alert dialog = new Alert(AlertType.ERROR, "Error removing product. Product is part of an order.", ButtonType.OK);
	    	        dialog.showAndWait();
	    	    });
	    	}
	    	initialize();
	    } else	{
	    	Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.ERROR, "Please select a product.", ButtonType.OK);
		        dialog.showAndWait();
		    });
	    }
    }
    

    @FXML
    public void changeSceneToMainView(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        Scene productManagementViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(productManagementViewScene);
        window.show();
    }
    

    @FXML
    private void switchToNewProductWindow(ActionEvent event) throws IOException {
    	/*
    	Parent mainViewParent = FXMLLoader.load(getClass().getResource("NewProduct.fxml"));
        Scene newProductViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newProductViewScene);
        window.show();
*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newProduct.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Create a product");
        stage.setScene(new Scene(loader.load()));

        stage.show();
        
        
    }
    /*
    @FXML
    public void changeSceneToProductDetailsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("singleProduct.fxml"));
        Parent orderViewParent = loader.load();

        Scene singleOrderViewScene = new Scene(orderViewParent);

        SingleProductController controller = loader.getController();
        controller.initData(productTable.getSelectionModel().getSelectedItem());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(singleOrderViewScene);
        window.show();
    }
    */
    
    @FXML
    private void changeSceneToProductDetailsView() throws IOException {
        
        
        Product p = productTable.getSelectionModel().getSelectedItem();
    	
	    if(p != null)	{
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("singleProduct.fxml"));

	        Stage stage = new Stage();
	        stage.setTitle("Product details");
	        stage.setScene(new Scene(loader.load()));

	        SingleProductController controller = loader.getController();
	        controller.initData(productTable.getSelectionModel().getSelectedItem());

	        stage.show();
	    	
	    } else	{
	    	Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.ERROR, "Please select a product.", ButtonType.OK);
		        dialog.showAndWait();
		    });
	    }
        
        
    }
}
