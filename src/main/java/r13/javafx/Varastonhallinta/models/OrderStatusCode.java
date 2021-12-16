package r13.javafx.Varastonhallinta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

// TODO: Auto-generated Javadoc
@Entity()
@Table(name = "\"OrderStatusCode\"")
public class OrderStatusCode {

    /** The id. */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private String id;

    /** The processed. */
    @Column(name = "processed")
    private Boolean processed;

    /** The description. */
    @Column(name = "description")
    private String description;

    /** The orders. */
    @OneToMany(mappedBy = "orderStatusCode")
    private List<Order> orders;

    /**
     * Instantiates a new order status code.
     *
     * @param processed the processed
     * @param description the description
     */
    public OrderStatusCode(Boolean processed, String description) {
        this.processed = processed;
        this.description = description;
    }

    /**
     * Instantiates a new order status code.
     */
    public OrderStatusCode() {

    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
