package r13.javafx.Varastonhallinta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    private void home(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    private void orders(MouseEvent event) throws IOException {
        loadPage("orders");
    }

    @FXML
    private void products(MouseEvent event) throws IOException {
        loadPage("products");
    }

    @FXML
    private void admin(MouseEvent event) throws IOException {
        loadPage("shiftsAdmin");
    }
    
    @FXML
    private void shifts(MouseEvent event) throws IOException {
        loadPage("shifts");
    }

    private void loadPage(String page) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        bp.setCenter(root);
    }
}
