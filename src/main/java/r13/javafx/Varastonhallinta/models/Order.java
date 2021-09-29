package r13.javafx.Varastonhallinta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity()
@Table(name = "\"Order\"")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "\"orderedAt\"")
    private Timestamp orderedAt;


    @ManyToOne
    @JoinColumn(name = "\"customerId\"", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "\"orderStatusCodeId\"")
    private OrderStatusCode orderStatusCode;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order(String id, Timestamp orderedAt) {
        this.id = id;
        this.orderedAt = orderedAt;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Timestamp orderedAt) {
        this.orderedAt = orderedAt;
    }


    public Customer getCustomer() {
        return customer;
    }


    public OrderStatusCode getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderStatusCode(OrderStatusCode orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
