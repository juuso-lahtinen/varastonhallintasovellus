package r13.javafx.Varastonhallinta;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

public class ProductEditController {
	
	private ProductAccessObject dao = new ProductAccessObject();
	
	private Product selectedProduct;
	
	@FXML
	private TextField productNameField;
	
	@FXML
	private TextField productDescriptionField;
	
	@FXML
	private TextField productLocationField;
	
	@FXML
	private TextField productPriceField;

	@FXML
	private Button saveBtn;
	
	@FXML
	private Button resetBtn;
	
	private SingleProductController singleProductController = new SingleProductController();
	private ProductController productManagementController = new ProductController();
	

	public void initData(Product product) {
        this.selectedProduct = product;
        
        if(selectedProduct != null) {

        	productNameField.setText(selectedProduct.getName());
        	productDescriptionField.setText(selectedProduct.getDescription());
        	productLocationField.setText(selectedProduct.getLocation());
        	productPriceField.setText(Double.toString(selectedProduct.getPrice()));
    	}
    }
	
	@FXML
	public void saveEdit()	{
		SingleProductController controller = new SingleProductController();
		
    	
    	Product product = new Product(selectedProduct.getId(), productNameField.getText(), Double.parseDouble(productPriceField.getText()), productDescriptionField.getText(), selectedProduct.getStock(), productLocationField.getText());
    	controller.initData(product);
    	if(dao.editProduct(product) != null)	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.INFORMATION, "Editing successful", ButtonType.OK);
    	        dialog.showAndWait();
    	    });
    	} else	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.INFORMATION, "Editing failed. Product with similar details exists", ButtonType.OK);
    	        dialog.showAndWait();
    	    });
    		
    	}
	}
	@FXML
	public void resetEdit()	{
		
		productNameField.setText(selectedProduct.getName());
    	productDescriptionField.setText(selectedProduct.getDescription());
    	productLocationField.setText(selectedProduct.getLocation());
    	productPriceField.setText(Double.toString(selectedProduct.getPrice()));
	}
	

	
	
}
