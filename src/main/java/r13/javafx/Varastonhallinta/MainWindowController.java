package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

    @FXML
    private Button orderControlBtn;

    @FXML
    private Button productControlBtn;


    @FXML
    private void switchToOrderSearch() throws IOException {
        App.setRoot("ordersearch");
    }

    @FXML
    private void switchToProductManagement() throws IOException {
        App.setRoot("productManagement");
    }
}