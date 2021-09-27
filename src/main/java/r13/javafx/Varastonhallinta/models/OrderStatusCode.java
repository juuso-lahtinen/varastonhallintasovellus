package r13.javafx.Varastonhallinta.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "\"OrderStatusCode\"")
public class OrderStatusCode {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "processed")
    private String processed;

    @Column(name = "description")
    private String description;

}
