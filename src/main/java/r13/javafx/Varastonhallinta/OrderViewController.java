package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Order;


public class OrderViewController implements Initializable {

    private Order selectedOrder;
    
    
    @FXML 
    private Label orderNumberLabel;
    @FXML 
    private Label orderDateLabel;    

    
    public void initData(Order  order)
    {
        selectedOrder = order;
        //orderNumberLabel.setText(selectedOrder.getOrderNumber());
        //orderDateLabel.setText(selectedOrder.getOrderDate());
    }
    
    
    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ordersearch.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    
    
}