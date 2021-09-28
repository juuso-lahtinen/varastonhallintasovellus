package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ProductCategoryAccessObject;
import r13.javafx.Varastonhallinta.models.Product;

import java.io.IOException;

public class ProductManagementController {

    private ProductAccessObject dao = new ProductAccessObject();

    @FXML
    private Button addBtn;

    @FXML
    private Button backButton;

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
        tableCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductCategory() == null ? "Uncategorized" : cellData.getValue().getProductCategory().getDescription()));

        productTable.setItems(getProducts());
    }

    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(dao.getProducts());
        return products;
    }

    @FXML
    private void switchToMainWindow() throws IOException {
        App.setRoot("mainwindow");
    }
}
