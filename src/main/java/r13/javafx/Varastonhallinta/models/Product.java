package r13.javafx.Varastonhallinta.models;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;


@Entity()
@Table(name = "\"Product\"")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "\"productCategoryId\"", nullable = true)     // Works
    private String categoryId;

    public Product(String id, String name, double price, String description, int stock, String location, String categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.location = location;
        this.categoryId = categoryId;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
