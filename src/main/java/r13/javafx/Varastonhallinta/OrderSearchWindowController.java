package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Order;

public class OrderSearchWindowController implements Initializable {

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField orderNumber;
    @FXML
    private TextField orderDate;
    @FXML
    private TextField productNumber;

    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, String> orderNumberColumn;
    @FXML
    private TableColumn<Order, String> orderDateColumn;
    @FXML
    private TableColumn<Order, String> orderSizeColumn;


    @FXML
    private Button detailedOrderViewButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNumber"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderDate"));
        orderSizeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderSize"));

        tableView.setItems(getOrders());
        System.out.println("test");

        //Disable the detailed order view button until a row is selected
        this.detailedOrderViewButton.setDisable(true);

    }

    public void userClickedOnTable() {
        this.detailedOrderViewButton.setDisable(false);
    }


    @FXML
    public void search(ActionEvent event) throws IOException {
        searchOrders();
    }

    @FXML
    private void searchOrders() throws IOException {
        //orderNumber.getText() / orderDate.getText() haku tietokantaan

    }

    public ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        return orders;
    }

    public void changeSceneToDetailedOrderView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("orderview.fxml")); //ei toimi
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        OrderViewController controller = loader.getController();
        controller.initData(tableView.getSelectionModel().getSelectedItem());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void switchToMainWindow() throws IOException {
        App.setRoot("mainwindow");
    }

}
