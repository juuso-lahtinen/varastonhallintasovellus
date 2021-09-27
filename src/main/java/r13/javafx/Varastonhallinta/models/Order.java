package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity()
@Table(name = "\"Order\"")
public class Order {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "\"orderedAt\"")
    private Timestamp orderedAt;

    @Column(name = "description")
    private String description;

    @Column(name = "\"orderStatusCodeId\"")
    private String orderStatusCodeId;

    public Order(String id, Timestamp orderedAt, String description) {
        this.id = id;
        this.orderedAt = orderedAt;
        this.description = description;
    }

    public Order() {
    }
}
