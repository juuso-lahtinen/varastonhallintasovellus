package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "\"firstName\"")
    private String firstName;

    @Column(name = "\"lastName\"")
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Shift> shifts;
    
    @Column(name = "\"isAdmin\"")
    private Boolean isAdmin;


    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = false;
    }

    public User(String username, String password, String firstName, String lastName, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public Shift getSingleShift(LocalDate date, LocalTime startTime, LocalTime endTime) {
        for (Shift s : shifts) {
            if (s.getDate().equals(date) && s.getStart().equals(startTime) && s.getEnd().equals(endTime)) {
                return s;
            }
        }
        return null;
    }
}
