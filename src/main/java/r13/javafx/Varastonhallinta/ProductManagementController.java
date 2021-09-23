package r13.javafx.Varastonhallinta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import r13.javafx.Varastonhallinta.models.DataAccessObject;
import r13.javafx.Varastonhallinta.models.Product;

import java.util.List;

public class ProductManagementController {

    private DataAccessObject dao = new DataAccessObject();

    @FXML
    private Button addBtn;

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
    private void addProduct() {

    }

    public void initialize() {
        tableId.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tableDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        tableStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        tableLocation.setCellValueFactory(new PropertyValueFactory<Product, String>("location"));
        tableCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("\"productCategoryId\""));

        productTable.setItems(getProducts());
    }

    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(dao.getProducts());
        return products;
    }
}
