package r13.javafx.Varastonhallinta.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity()
@Table(name = "\"Product\"")
public class Product {

	/*
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;
    */
	
	@Id
    @Column(name = "id", unique = true)
    private String id;
    
    @PrePersist
    private void ensureId(){
        this.setId(UUID.randomUUID().toString());
    }
	

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

    @ManyToOne
    @JoinColumn(name = "\"productCategoryId\"", nullable = true)
    private ProductCategory productCategory;

    public Product(String id, String name, double price, String description, int stock, String location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.location = location;
    }

    public Product() {
    }

    /*
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    */
    
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


    public ProductCategory getProductCategory() {
        return productCategory;
    }
}
