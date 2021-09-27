package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;

@Entity()
@Table(name = "\"OrderItem\"")
public class OrderItem {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "orderId")
    private String orderId;

    @Column(name = "productId")
    private String productId;
}
