package r13.javafx.Varastonhallinta;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id", unique = true)
    private int id;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
