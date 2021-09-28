package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

public class NewProductController {
	
    private ProductAccessObject dao = new ProductAccessObject();

    @FXML
    private Button addBtn;
    
    @FXML
    private TextField productIdField;
    
    @FXML
    private TextField productNameField;
    
    @FXML
    private TextArea productDescriptionField;
    
    @FXML
    private TextField productPriceField;
    
    @FXML
    private TextField productLocationField;
    
    
    @FXML
    private Button backButton;
    

   

    
    @FXML
    
    
    private void addProduct() {
    	if(dao.addProduct(productNameField.getText(), Double.parseDouble(productPriceField.getText()), productDescriptionField.getText(), 0, productLocationField.getText()))	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.INFORMATION, "Success", ButtonType.OK);
    	        dialog.showAndWait();
    	        try {
					switchToProductManagementWindow();
				} catch (IOException e) {
					e.printStackTrace();
				}
    	    });
    	} else	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.ERROR, "Error adding product", ButtonType.OK);
    	        dialog.showAndWait();
    	    });
    	}
    }



    
    @FXML
    private void switchToProductManagementWindow() throws IOException {
        App.setRoot("productManagement");
    }
    

}
