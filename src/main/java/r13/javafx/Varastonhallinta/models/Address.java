package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;

@Entity()
@Table(name = "\"Address\"")
public class Address {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "postal")
    private String postal;

    @OneToOne(mappedBy = "address")
    private Customer customer;

    public Address(String id, String address, String city, String postal, Customer customer) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.postal = postal;
        this.customer = customer;
    }

    public Address() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
