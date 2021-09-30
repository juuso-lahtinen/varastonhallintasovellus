package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML
    private Button orderControlBtn;

    @FXML
    private Button productControlBtn;


    public void changeSceneToOrderManagementView(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("orderManagement.fxml"));
        Scene orderManagementViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(orderManagementViewScene);
        window.show();
    }

    public void changeSceneToProductManagementView(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("productManagement.fxml"));
        Scene productManagementViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(productManagementViewScene);
        window.show();
    }

    @FXML
    private void switchToProductManagement() throws IOException {
        App.setRoot("productManagement");
    }
}