package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

public class NewProductController {
	
	ResourceBundle bundle = Singleton.getInstance().getBundle();	
	
    private ProductAccessObject dao = new ProductAccessObject();

    @FXML
    private Button addBtn;
    
    @FXML
    private TextField productIdField;
    
    @FXML
    private TextField productNameField;
    
    @FXML
    private TextField productDescriptionField;
    
    @FXML
    private TextField productPriceField;
    
    @FXML
    private TextField productLocationField;
    
    
    @FXML
    private Button backButton;
    
    @FXML
    private VBox anchorPane2;
    
    
    @FXML
    private void addProduct() {
    	if(productNameField.getText().trim().equals("") || productPriceField.getText().trim().equals("") || productLocationField.getText().trim().equals(""))	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.ERROR, bundle.getString("fillAll"), ButtonType.OK);
    	        dialog.showAndWait();
    	    });
    		
    	} else	{
    		Product product = new Product ("123", productNameField.getText(), Double.parseDouble(productPriceField.getText()), productDescriptionField.getText(), 0, productLocationField.getText());
    		if(dao.addProduct(product) != null)	{
        		Platform.runLater(() -> {
        	        Alert dialog = new Alert(AlertType.INFORMATION, bundle.getString("addSuccessful"), ButtonType.OK);
        	        dialog.showAndWait();
        	        clear();
        	    });
        	} else	{
        		Platform.runLater(() -> {
        	        Alert dialog = new Alert(AlertType.ERROR, bundle.getString("addError"), ButtonType.OK);
        	        dialog.showAndWait();
        	    });
        	}

    	}
    }


    
    private void clear() {
    	for (Node node : anchorPane2.getChildren()) {
    	    if (node instanceof TextField) {
    	        ((TextField)node).setText("");
    	    } else if (node instanceof TextArea)	{
    	    	((TextArea)node).setText("");
    	    }
    	}
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
