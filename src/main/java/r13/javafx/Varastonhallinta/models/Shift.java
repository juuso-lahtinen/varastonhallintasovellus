package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "\"Shift\"")
public class Shift {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

    @ManyToOne
    @JoinColumn(name = "\"userId\"")
    private User user;

    @Column(name = "\"startTime\"")
    private LocalTime startTime;

    @Column(name = "\"endTime\"")
    private LocalTime endTime;

    @Column(name = "date")
    private LocalDate date;

    public Shift(User user, LocalTime startTime, LocalTime endTime, LocalDate timestamp) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = timestamp;
    }

    public Shift(LocalTime start, LocalTime end, LocalDate date) {
        this.startTime = start;
        this.endTime = end;
        this.date = date;
    }

    public Shift(LocalTime start, LocalTime end) {
        this.startTime = start;
        this.endTime = end;
    }

    public Shift() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalTime getStart() {
        return startTime;
    }

    public void setStart(LocalTime start) {
        this.startTime = start;
    }

    public LocalTime getEnd() {
        return endTime;
    }

    public void setEnd(LocalTime end) {
        this.endTime = end;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
